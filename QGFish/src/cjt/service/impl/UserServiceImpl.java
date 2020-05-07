package cjt.service.impl;

import cjt.dao.FindDao;
import cjt.dao.UserDao;
import cjt.dao.impl.FindDaoImpl;
import cjt.dao.impl.UserDaoImpl;
import cjt.model.Page;
import cjt.model.Product;
import cjt.model.Shopping;
import cjt.model.User;
import cjt.model.dto.ResultInfo;
import cjt.service.FindService;
import cjt.service.UserService;

import javax.servlet.http.Part;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static cjt.util.Md5Util.getMD5String;

/**
 * @author cjt
 */
public class UserServiceImpl implements UserService {
    /**
     * 用户登陆
     * @param user
     * @return
     */
    @Override
    public ResultInfo login(User user) {
        //判断用户输入是否合法
        if (user.getUserName().length() != 0 && user.getPassword().length() != 0) {
            //将密码md5加密对应数据库的已加密的密码
            String password = getMD5String(user.getPassword());
            user.setPassword(password);
            UserDao userDao = new UserDaoImpl();
            //传入用户信息与数据库进行校验
            return userDao.login(user);
        } else {
            return new ResultInfo(false, "用户名和密码不能为空，请重新输入", null);
        }
    }

    /**
     * 用户注册
     * @param user
     * @param checkCode_session
     * @return
     */
    @Override
    public ResultInfo register(User user, String checkCode_session) {
        if (checkCode_session.equalsIgnoreCase(user.getCheckCode())) {
            //判断是否输入信息为空
            if (user.getUserName().length() != 0 && user.getPassword().length() != 0 && user.getEmail().length() != 0
                    && user.getPhone().length() != 0 && user.getAddress().length() != 0) {
                //设置正则表达式校验邮箱格式
                String regex = "\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";
                if (user.getEmail().matches(regex)) {
                    //将密码md5加密
                    String password = getMD5String(user.getPassword());
                    //封装用户信息
                    user.setPassword(password);
                    user.setCondition("正常");
                    UserDao userDao = new UserDaoImpl();
                    //将用户填写的信息存入数据库
                    return userDao.register(user);
                } else {
                    return new ResultInfo(false, "邮箱格式不合法", null);
                }
            } else {
                return new ResultInfo(false, "请完整填写信息", null);
            }
        } else {
            return new ResultInfo(false, "验证码错误", null);
        }
    }

    /**
     * 卖家上传商品
     * @param product
     * @return
     */
    @Override
    public ResultInfo release(Product product) {
        //判断用户输入是否合法
        if (product.getProductName().length() != 0 && product.getProductKind().length() != 0) {
            if(product.getProductPrice()>0&&product.getProductAmount()>0){
                //为商品初始化出货量
                product.setProductSold(0);
                //为商品初始化得分
                product.setProductScore(3);
                product.setProductScoreTime(1);
                product.setProductStarLevel(3.0);
                //为商品初始化状态
                product.setProductCondition("待审核");
                UserDao userDao = new UserDaoImpl();
                //将用户填写的信息存入数据库
                return userDao.release(product);
            }
            return new ResultInfo(false, "商品价格和数量需要大于0", null);
        }
        else {
            return new ResultInfo(false, "请填写完整商品信息", null);
        }
    }

    @Override
    public ResultInfo update(User user) {
        if (user.getUserName().length() != 0&& user.getEmail().length() != 0
                && user.getPhone().length() != 0 && user.getAddress().length() != 0) {
            //设置正则表达式校验邮箱格式
            String regex = "\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";
            if (user.getEmail().matches(regex)) {
                UserDao userDao = new UserDaoImpl();
                //将用户填写的信息存入数据库
                return userDao.update(user);
            } else {
                return new ResultInfo(false, "邮箱格式不合法", null);
            }
        } else {
            return new ResultInfo(false, "请完整填写信息", null);
        }
    }

    @Override
    public ResultInfo updatePassword(String userId,String oldPassword, String newPassword1, String newPassword2) {
        //先判断密码输入栏是否有空的
        if(oldPassword.length()!=0&&newPassword1.length()!=0&&newPassword2.length()!=0){
            //再判断两次输入的新密码是否一致
            if(newPassword1.equals(newPassword2)){
                //查找用户原本密码
                FindService findService=new FindServiceImpl();
                User user=findService.findUser(Integer.parseInt(userId));
                //判断用户输入的旧密码是否正确
                String password1 = getMD5String(oldPassword);
                if(user.getPassword().equals(password1)){
                    UserDao userDao= new UserDaoImpl();
                    //正确则修改密码
                    String password2 = getMD5String(newPassword2);
                    user.setPassword(password2);
                    return userDao.updatePassword(user);
                }
                else{
                    return new ResultInfo(false,"请输入正确的原先密码",null);
                }
            }
            else{
                return new ResultInfo(false,"两次输入的新密码不一致",null);
            }
        }
        else{
            return new ResultInfo(false,"密码不能为空",null);
        }
    }

    /**
     * 卖家上传商品图片
     * @param productId
     * @param filePart
     * @param realPath
     * @return
     * @throws IOException
     */
    @Override
    public boolean releasePicture(String productId,Part filePart, String realPath,String filename) throws IOException {
        //若添加了图片，则存储
        if(filename!=null) {
            //以当前时间为图片命名，防止重复
            String str = (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(new Date());
            //取文件后缀
            String[] s=filename.split("\\.");
            //防止数组越界
            if(s.length>1) {
                //拼接文件名
                String fileName = str + "." + s[1];
                //将图片存入本地，路径为realPath
                filePart.write(realPath + "\\" + fileName);
                UserDao userDao = new UserDaoImpl();
                return userDao.uploadPicture(Integer.parseInt(productId), fileName);
            }
            else{
                return false;
            }
        }
        else {
            //当没有添加图片
            return false;
        }
    }

    /**
     * 分页模糊查询商品
     * @param currentPageStr
     * @param likeProductName
     * @param likeKind
     * @param radio
     * @return
     */
    @Override
    public ResultInfo findProductByPage(String currentPageStr,String likeProductName,String likeKind,String radio) {
        Page<Product> page=new Page<>();
        int currentPage=Integer.parseInt(currentPageStr);
        int rows=4;
        //设置参数
        page.setCurrentPage(currentPage);
        page.setRows(rows);
        //调用dao查询商品总记录数
        UserDao userDao=new UserDaoImpl();
        int totalCount=userDao.findProductTotalCount(likeProductName,likeKind);
        page.setTotalCount(totalCount);
        //计算开始的记录索引
        int start = (currentPage-1)*rows;
        //计算总页码
        int totalPage = (totalCount % rows ==0) ? (totalCount/rows) : (totalCount/rows)+1 ;
        page.setTotalPage(totalPage);
        //返回每页的数据集合
        List<Product> list=userDao.findProductByPage(start,rows,likeProductName,likeKind,radio);
        page.setList(list);
        System.out.println(page);
        if(list!=null){
            return new ResultInfo(true,"分页查询完毕",page);
        }
        else{
            return new ResultInfo(false,"查询结果为空",page);
        }
    }

    /**
     * 了解商品详情
     * @param product
     * @return
     */
    @Override
    public ResultInfo read(Product product) {
        FindService findService=new FindServiceImpl();
        //通过商品id查找完整的product信息
        product=findService.findProduct(product.getProductId());
        //将完整的product对象存入ResultInfo
        if(product!=null){
            return new ResultInfo(true,"查询成功",product);
        }
        else{
            return new ResultInfo(false,"数据库连接失败",null);
        }
    }

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
            UserDao userDao = new UserDaoImpl();
            return userDao.addShopping(shopping);
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
            UserDao userDao = new UserDaoImpl();
            return userDao.buy(shopping);
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
        if(product.getProductAmount()>=shopping.getBuyAmount()) {
            //计算订单总金额
            shopping.setTotalPrice(product.getProductPrice() * shopping.getBuyAmount());
            User user=findService.findUser(shopping.getBuyer());
            //根据用户id查询用户住址，用户住址即为订单发往地址
            shopping.setAddress(user.getAddress());
            UserDao userDao = new UserDaoImpl();
            return userDao.buyInShopping(shopping);
        }
        else{
            return new ResultInfo(false,"购买数量不能超过商品数量",null);
        }
    }

    /**
     * type=1时，分页查询购物车
     * type=2时，分页查询我的商品，即我收到的订单请求
     * @param currentPageStr
     * @param userIdStr
     * @param type
     * @return
     */
    @Override
    public ResultInfo findShoppingByPage(String currentPageStr,String userIdStr,int type) {
        Page<Shopping> page=new Page<>();
        //转换类型
        int currentPage=Integer.parseInt(currentPageStr);
        int userId=Integer.parseInt(userIdStr);
        int rows=4;
        //设置参数
        page.setCurrentPage(currentPage);
        page.setRows(rows);
        //调用dao查询商品总记录数
        UserDao userDao=new UserDaoImpl();
        int totalCount=userDao.findShoppingTotalCount(userId,type);
        page.setTotalCount(totalCount);
        //计算开始的记录索引
        int start = (currentPage-1)*rows;
        //计算总页码
        int totalPage = (totalCount % rows ==0) ? (totalCount/rows) : (totalCount/rows)+1 ;
        page.setTotalPage(totalPage);
        //返回每页的数据集合
        List<Shopping> list=userDao.findShoppingByPage(start,rows,userId,type);
        page.setList(list);
        System.out.println(page);
        if(list!=null){
            return new ResultInfo(true,"分页查询完毕",page);
        }
        else{
            return new ResultInfo(false,"查询结果为空",page);
        }
    }

    /**
     * 从购物车中删除
     * @param shoppingIdStr
     * @return
     */
    @Override
    public ResultInfo deleteInShopping(String shoppingIdStr) {
        int shoppingId=Integer.parseInt(shoppingIdStr);
        UserDao userDao=new UserDaoImpl();
        return userDao.deleteInShopping(shoppingId);
    }

    /**
     * 卖家确认订单，确认发货
     * @param shoppingIdStr
     * @return
     */
    @Override
    public ResultInfo allowBuy(String shoppingIdStr) {
        int shoppingId=Integer.parseInt(shoppingIdStr);
        UserDao userDao=new UserDaoImpl();
        return userDao.allowBuy(shoppingId);
    }

}
