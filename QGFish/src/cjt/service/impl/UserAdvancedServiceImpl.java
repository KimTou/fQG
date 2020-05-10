package cjt.service.impl;

import cjt.dao.FindDao;
import cjt.dao.UserAdvancedDao;
import cjt.dao.impl.FindDaoImpl;
import cjt.dao.impl.UserAdvancedDaoImpl;
import cjt.model.*;
import cjt.model.dto.ResultInfo;
import cjt.service.FindService;
import cjt.service.UserAdvancedService;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author cjt
 * 用户高级功能业务（商品，购物车，订单，申诉）
 */
public class UserAdvancedServiceImpl implements UserAdvancedService {
    /**
     * 加入购物车
     * @param shopping
     * @return
     */
    @Override
    public ResultInfo addShopping(Shopping shopping) {
        FindDao findDao=new FindDaoImpl();
        //只有用户没有添加该商品进购物车，才允许加入购物车
        if(findDao.findShopping(shopping.getProductId(),shopping.getBuyer())!=true) {
            FindService findService = new FindServiceImpl();
            //根据商品id查询商品完整信息
            Product product = findService.findProduct(shopping.getProductId());
            //封装完整的购物单信息
            shopping.setProductName(product.getProductName());
            shopping.setProductKind(product.getProductKind());
            shopping.setProductPrice(product.getProductPrice());
            shopping.setProductAmount(product.getProductAmount());
            shopping.setSeller(product.getProductSeller());
            //此时从product导出来的picture已带有upload
            shopping.setProductPicture(product.getProductPicture());
            //被加入购物车商品的初始状态
            shopping.setProductCondition("加入购物车");
            UserAdvancedDao userAdvancedDao=new UserAdvancedDaoImpl();
            return userAdvancedDao.addShopping(shopping);
        }
        else{
            return new ResultInfo(false,"你已将该商品加入购物车",shopping);
        }
    }

    /**
     * 用户直接购买商品
     * @param shopping
     * @return
     */
    @Override
    public ResultInfo buy(Shopping shopping) {
        FindService findService = new FindServiceImpl();
        //根据商品id查询商品完整信息
        Product product = findService.findProduct(shopping.getProductId());
        //判断用户购买数量是否大于商品现有数量
        if(product.getProductAmount()>=shopping.getBuyAmount()) {
            //封装完整的购物单信息
            shopping.setProductName(product.getProductName());
            shopping.setProductKind(product.getProductKind());
            shopping.setProductPrice(product.getProductPrice());
            shopping.setProductAmount(product.getProductAmount());
            //比加入购物车多了一条购买数量，所以可以计算总金额
            shopping.setTotalPrice(product.getProductPrice() * shopping.getBuyAmount());
            shopping.setSeller(product.getProductSeller());
            shopping.setProductPicture(product.getProductPicture());
            //被直接购买商品的初始状态
            shopping.setProductCondition("等待卖家审核");
            User user=findService.findUser(shopping.getBuyer());
            //根据用户id查询用户住址，用户住址即为订单发往地址
            shopping.setAddress(user.getAddress());
            UserAdvancedDao userAdvancedDao=new UserAdvancedDaoImpl();
            return userAdvancedDao.buy(shopping);
        }
        else{
            return new ResultInfo(false,"购买数量不能超过商品数量",null);
        }
    }

    /**
     * 购买购物车的商品
     * @param shopping
     * @return
     */
    @Override
    public ResultInfo buyInShopping(Shopping shopping) {
        FindService findService = new FindServiceImpl();
        //根据商品id查询商品完整信息
        Product product = findService.findProduct(shopping.getProductId());
        //判断用户购买数量是否大于商品现有数量
        if(product.getProductAmount()>=shopping.getBuyAmount()) {
            //计算订单总金额
            shopping.setTotalPrice(product.getProductPrice() * shopping.getBuyAmount());
            User user=findService.findUser(shopping.getBuyer());
            //根据用户id查询用户住址，用户住址即为订单发往地址
            shopping.setAddress(user.getAddress());
            UserAdvancedDao userAdvancedDao=new UserAdvancedDaoImpl();
            return userAdvancedDao.buyInShopping(shopping);
        }
        else{
            return new ResultInfo(false,"购买数量不能超过商品数量",null);
        }
    }

    /**
     * type=1时，分页查询购物车
     * type=2时，分页查询我的商品，即我收到的订单请求
     * type=3，分页查询我的订单，即卖家已发货的
     * @param currentPage
     * @param userId
     * @param type
     * @return
     */
    @Override
    public ResultInfo findShoppingByPage(int currentPage,int userId,int type) {
        Page<Shopping> page=new Page<>();
        //转换类型
        int rows=4;
        //设置参数
        page.setCurrentPage(currentPage);
        page.setRows(rows);
        //调用dao查询订单总记录数
        UserAdvancedDao userAdvancedDao=new UserAdvancedDaoImpl();
        int totalCount= userAdvancedDao.findShoppingTotalCount(userId,type);
        page.setTotalCount(totalCount);
        //计算开始的记录索引
        int start = (currentPage-1)*rows;
        //计算总页码
        int totalPage = (totalCount % rows ==0) ? (totalCount/rows) : (totalCount/rows)+1 ;
        page.setTotalPage(totalPage);
        //返回每页的数据集合
        List<Shopping> list= userAdvancedDao.findShoppingByPage(start,rows,userId,type);
        page.setList(list);
        if(list!=null){
            return new ResultInfo(true,"分页查询完毕",page);
        }
        else{
            return new ResultInfo(false,"查询结果为空",page);
        }
    }

    /**
     * 用户从购物车中删除商品
     * 卖家拒绝订单
     * 买家取消订单
     * @param shoppingId
     * @return
     */
    @Override
    public ResultInfo deleteInShopping(int shoppingId) {
        //根据订单编号直接删除订单
        UserAdvancedDao userAdvancedDao=new UserAdvancedDaoImpl();
        return userAdvancedDao.deleteInShopping(shoppingId);
    }

    /**
     * 卖家确认订单，确认发货
     * @param shoppingId
     * @return
     */
    @Override
    public ResultInfo allowBuy(int shoppingId) {
        //首先判断用户是否具备售卖权限
        FindService findService=new FindServiceImpl();
        //根据订单id查找到订单信息
        Shopping shopping=findService.findShopping(shoppingId);
        //根据订单信息的seller查找到用户信息
        User user=findService.findUser(shopping.getSeller());
        //不具备售卖权限则无法发货
        if("正常".equals(user.getCondition())) {
            UserAdvancedDao userAdvancedDao=new UserAdvancedDaoImpl();
            return userAdvancedDao.allowBuy(shoppingId);
        }
        else{
            return new ResultInfo(false,"你已被禁止售卖!\n原因："+user.getLabel(),null);
        }
    }

    /**
     * 下载订单文件
     * @param shoppingIdStr
     * @return
     * @throws IOException
     */
    @Override
    public String downLoad(String shoppingIdStr) throws IOException{
        FindService findService=new FindServiceImpl();
        //根据订单id查找到订单的详细信息
        Shopping shopping=findService.findShopping(Integer.parseInt(shoppingIdStr));
        String path="D:\\upload\\"+"order"+shopping.getShoppingId()+".txt";
        //以txt格式写文件
        FileWriter fw = new FileWriter(path);
        //编写订单文件
        String str1 = "尊敬的客户，以下时您的订单详情：\n\n";
        String str2 = "订单编号："+shopping.getShoppingId()+"\n";
        String str3 = "商品编号："+shopping.getProductId()+"   商品名称："+shopping.getProductName()+"   商品种类："+shopping.getProductKind()+"\n";
        String str4 = "商品价格："+shopping.getProductPrice()+"   购买数量："+shopping.getBuyAmount()+"   总金额："+shopping.getTotalPrice()+"\n";
        String str5 = "卖家id："+shopping.getSeller()+"   买家id："+shopping.getBuyer()+"\n";
        String str6 = "送货地址："+shopping.getAddress()+"\n\n感谢您的光顾！";
        fw.write(str1);
        fw.write(str2);
        fw.write(str3);
        fw.write(str4);
        fw.write(str5);
        fw.write(str6);
        //关闭流
        fw.close();
        //返回文件的路径
        return path;
    }

    /**
     * 用户评价并结束订单
     * @param shoppingId
     * @param score
     * @param comment
     * @return
     */
    @Override
    public ResultInfo evaluate(int shoppingId, int score, String comment) {
        FindService findService=new FindServiceImpl();
        Shopping shopping=findService.findShopping(shoppingId);
        //通过订单的商品id查找到商品
        Product product=findService.findProduct(shopping.getProductId());
        if(score==0){
            //在商品评论后加上，以换行符区分
            if(comment != null && comment.length() != 0) {
                product.setProductComment(product.getProductComment() + "用户" + shopping.getBuyer() + "评论：" + comment);
            }
        }
        if(score!=0){
            //若得分不为0，即用户填写评分，则进行统计
            product.setProductScore(product.getProductScore()+score);
            product.setProductScoreTime(product.getProductScoreTime()+1);
            //星级=总得分/得分次数（需进行强制转换）
            product.setProductStarLevel((double)product.getProductScore()/product.getProductScoreTime());
            if(comment != null && comment.length() != 0) {
                //在商品评论后加上，以换行符区分
                product.setProductComment(product.getProductComment() + "用户" + shopping.getBuyer() + "评论：" + comment);
            }
        }
        //不管有没有评价，出货量和商品剩余数量都会改变
        product.setProductAmount(product.getProductAmount()-shopping.getBuyAmount());
        //商品剩余数量=原有数量-购买数量
        //出货量=原出货量+购买数量
        product.setProductSold(product.getProductSold()+shopping.getBuyAmount());
        UserAdvancedDao userAdvancedDao=new UserAdvancedDaoImpl();
        //通过订单的商品id查找到商品后，订单就完成任务了，被删除
        userAdvancedDao.deleteInShopping(shoppingId);
        return userAdvancedDao.evaluate(product);
    }

    /**
     * 卖家查询自己发布的二手商品
     * @param currentPage
     * @param userId
     * @return
     */
    @Override
    public ResultInfo findMyProduct(int currentPage, int userId) {
        Page<Product> page=new Page<>();
        //转换类型
        int rows=4;
        //设置参数
        page.setCurrentPage(currentPage);
        page.setRows(rows);
        //调用dao查询商品总记录数
        UserAdvancedDao userAdvancedDao=new UserAdvancedDaoImpl();
        int totalCount= userAdvancedDao.findMyProductTotalCount(userId);
        page.setTotalCount(totalCount);
        //计算开始的记录索引
        int start = (currentPage-1)*rows;
        //计算总页码
        int totalPage = (totalCount % rows ==0) ? (totalCount/rows) : (totalCount/rows)+1 ;
        page.setTotalPage(totalPage);
        //返回每页的数据集合
        List<Product> list= userAdvancedDao.findMyProduct(start,rows,userId);
        page.setList(list);
        if(list!=null){
            return new ResultInfo(true,"分页查询完毕",page);
        }
        else{
            return new ResultInfo(false,"查询结果为空",page);
        }
    }

    /**
     * 卖家回复用户评论
     * @param comment
     * @param productId
     * @return
     */
    @Override
    public ResultInfo reply(String comment, int productId) {
        FindService findService=new FindServiceImpl();
        //通过商品id查询商品
        Product product=findService.findProduct(productId);
        //有评论才进行回复
        if(comment!=null&&comment.length()!=0) {
            //连接评论
            product.setProductComment(product.getProductComment()  + "卖家回复：" + comment);
            UserAdvancedDao userAdvancedDao=new UserAdvancedDaoImpl();
            return userAdvancedDao.reply(product);
        }
        //空则表示卖家取消了评论
        return new ResultInfo(false,"取消成功",null);
    }

    /**
     * 修改订单信息
     * @param shopping
     * @return
     */
    @Override
    public ResultInfo updateShopping(Shopping shopping) {
        //首先判断是否有空的地方
        if(shopping.getProductName().length()!=0&&shopping.getProductKind().length()!=0&&shopping.getAddress().length()!=0) {
            //数字已做了限制
            UserAdvancedDao userAdvancedDao = new UserAdvancedDaoImpl();
            return userAdvancedDao.updateShopping(shopping);
        }
        else {
            return new ResultInfo(false,"输入信息不能为空",null);
        }
    }

    /**
     * 用户提交申诉信息
     * @param appeal
     * @return
     */
    @Override
    public ResultInfo appeal(Appeal appeal) {
        //在前端已保证信息部位空，但为了严谨性，还是再验证一下
        if(appeal.getAppealTitle().length()!=0&&appeal.getAppealContent().length()!=0){
            UserAdvancedDao userAdvancedDao=new UserAdvancedDaoImpl();
            return userAdvancedDao.appeal(appeal);
        }
        return new ResultInfo(false,"请填写完整信息",null);
    }
}
