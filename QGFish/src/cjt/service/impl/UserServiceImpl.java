package cjt.service.impl;

import cjt.dao.FindDao;
import cjt.dao.UserDao;
import cjt.dao.impl.FindDaoImpl;
import cjt.dao.impl.UserDaoImpl;
import cjt.model.*;
import cjt.model.dto.ResultInfo;
import cjt.service.FindService;
import cjt.service.UserService;
import cjt.util.SendMail;

import javax.servlet.http.Part;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
                //设置正则表达式校验邮箱格式和手机号码格式
                String regexEmail = "\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";
                String regexPhone = "1[3578][0-9]{9}";
                if (user.getEmail().matches(regexEmail)&&user.getPhone().matches(regexPhone)) {
                    FindDao findDao=new FindDaoImpl();
                    //验证是否有信息被注册过，保证信息的唯一性
                    if(findDao.findUser(user.getUserName(),user.getEmail(),user.getPhone())!=true) {
                        //将密码md5加密
                        String password = getMD5String(user.getPassword());
                        //封装用户信息
                        user.setPassword(password);
                        user.setCondition("正常");
                        UserDao userDao = new UserDaoImpl();
                        //将用户填写的信息存入数据库
                        return userDao.register(user);
                    }
                    else{
                        return new ResultInfo(false,"用户名或邮箱或电话已注册过",null);
                    }
                } else {
                    return new ResultInfo(false, "邮箱或手机号码格式不合法", null);
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
        FindService findService=new FindServiceImpl();
        User user=findService.findUser(product.getProductSeller());
        //首先判断用户是否有权限售卖商品
        if(user.getCondition().equals("正常")) {
            //判断用户输入是否合法
            if (product.getProductName().length() != 0 && product.getProductKind().length() != 0) {
                if (product.getProductPrice() > 0 && product.getProductAmount() > 0) {
                    //为商品初始化出货量
                    product.setProductSold(0);
                    //为商品初始化得分
                    product.setProductScore(3);
                    product.setProductScoreTime(1);
                    product.setProductStarLevel(3.0);
                    product.setProductComment(" ");
                    //为商品初始化状态
                    product.setProductCondition("待审核");
                    UserDao userDao = new UserDaoImpl();
                    //将用户填写的信息存入数据库
                    return userDao.release(product);
                }
                return new ResultInfo(false, "商品价格和数量需要大于0", null);
            } else {
                return new ResultInfo(false, "请填写完整商品信息", null);
            }
        }
        else{
            return new ResultInfo(false,"你已被禁止售卖!\n原因："+user.getLabel(),null);
        }
    }

    /**
     * 用户修改个人信息
     * @param user
     * @return
     */
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

    /**
     * 用户修改密码
     * @param userId
     * @param oldPassword
     * @param newPassword1
     * @param newPassword2
     * @return
     */
    @Override
    public ResultInfo updatePassword(int userId,String oldPassword, String newPassword1, String newPassword2) {
        //先判断密码输入栏是否有空的
        if(oldPassword.length()!=0&&newPassword1.length()!=0&&newPassword2.length()!=0){
            //再判断两次输入的新密码是否一致
            if(newPassword1.equals(newPassword2)){
                //查找用户原本密码
                FindService findService=new FindServiceImpl();
                User user=findService.findUser(userId);
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
     * 用户忘记密码，找回密码
     * @param email
     * @return
     */
    @Override
    public ResultInfo findBackPassword(String email) {
        FindDao findDao=new FindDaoImpl();
        System.out.println(email);
        //若正确，返回封装了用户id的对象 ；若错误，返回null
        User user=findDao.findUser(email);
        //首先判断用户邮箱是否输入正确
        if(user!=null) {
            String str = "1234567890";
            //生成随机角标
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            //随机生成8位数字的密码
            for (int i = 1; i <= 8; i++) {
                int index = random.nextInt(str.length());
                //获取随机字符
                char ch = str.charAt(index);
                sb.append(ch);
            }
            //得到新的密码
            String newPassword = sb.toString();
            System.out.println(newPassword);
            //重置用户密码,将密码md5加密
            user.setPassword(getMD5String(newPassword));
            UserDao userDao=new UserDaoImpl();
            //先判断是否重置成功，成功后再发给用户邮件，以免误导用户
            if(userDao.updatePassword(user).isStatus()==true){
                String mailContent = "您的密码已重置为：" + newPassword + "（请根据重置的密码进行登陆并尽快修改密码。感谢您的使用！）";
                //实例化一个发送邮件的对象
                SendMail mySendMail = new SendMail();
                //设置收件人和消息内容
                mySendMail.sendMail(email, mailContent);
                return new ResultInfo(true,"操作成功，快去您的邮箱看看吧",mailContent);
            }
            else{
                return new ResultInfo(false,"重置失败",null);
            }
        }
        else{
            return new ResultInfo(false,"邮箱错误",null);
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
        if(filePart!=null&&filename!=null) {
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
                //添加失败，也附加一张无图片
                UserDao userDao = new UserDaoImpl();
                return userDao.uploadPicture(Integer.parseInt(productId), "noPic.jpg");
            }
        }
        else {
            //当没有添加图片，则附一张无图片
            UserDao userDao = new UserDaoImpl();
            return userDao.uploadPicture(Integer.parseInt(productId),"noPic.jpg");
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
        UserDao userDao=new UserDaoImpl();
        return userDao.deleteInShopping(shoppingId);
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
        Shopping shopping=findService.findShopping(shoppingId);
        User user=findService.findUser(shopping.getSeller());
        //不具备权限则无法发货售卖
        if(user.getCondition().equals("正常")) {
            UserDao userDao = new UserDaoImpl();
            return userDao.allowBuy(shoppingId);
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
        Shopping shopping=findService.findShopping(Integer.parseInt(shoppingIdStr));
        String path="D:\\upload\\"+"order"+shopping.getShoppingId()+".txt";
        FileWriter fw = new FileWriter(path);
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
        fw.close();
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
                product.setProductComment(product.getProductComment() + "\n" + "用户" + shopping.getBuyer() + "评论：" + comment);
            }
        }
        if(score!=0){
            product.setProductScore(product.getProductScore()+score);
            product.setProductScoreTime(product.getProductScoreTime()+1);
            //星级=总得分/得分次数
            product.setProductStarLevel(product.getProductScore()/product.getProductScoreTime());
            if(comment != null && comment.length() != 0) {
                //在商品评论后加上，以换行符区分
                product.setProductComment(product.getProductComment() + "\n" + "用户" + shopping.getBuyer() + "评论：" + comment);
            }
        }
        //不管有没有评价，出货量和商品剩余数量都会改变
        product.setProductAmount(product.getProductAmount()-shopping.getBuyAmount());
        //商品剩余数量=原有数量-购买数量
        //出货量=原出货量+购买数量
        product.setProductSold(product.getProductSold()+shopping.getBuyAmount());
        UserDao userDao=new UserDaoImpl();
        //通过订单的商品id查找到商品后，订单就完成任务了，被删除
        userDao.deleteInShopping(shoppingId);
        return userDao.evaluate(product);
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
        UserDao userDao=new UserDaoImpl();
        int totalCount=userDao.findMyProductTotalCount(userId);
        page.setTotalCount(totalCount);
        //计算开始的记录索引
        int start = (currentPage-1)*rows;
        //计算总页码
        int totalPage = (totalCount % rows ==0) ? (totalCount/rows) : (totalCount/rows)+1 ;
        page.setTotalPage(totalPage);
        //返回每页的数据集合
        List<Product> list=userDao.findMyProduct(start,rows,userId);
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
        if(comment!=null&&comment.length()!=0) {
            product.setProductComment(product.getProductComment()  + "卖家回复：" + comment);
            UserDao userDao=new UserDaoImpl();
            return userDao.reply(product);
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
        //纯粹修改订单信息，直接就可带入，因为数字已做了限制
        UserDao userDao=new UserDaoImpl();
        return userDao.updateShopping(shopping);
    }

    /**
     * 用户提交申诉信息
     * @param appeal
     * @return
     */
    @Override
    public ResultInfo appeal(Appeal appeal) {
        //在前端已保证信息部位空，但为了严谨性，还是再验证一下
        if(appeal.getAppealTitle()!=null&&appeal.getAppealTitle()!=""&&appeal.getAppealContent()!=null&&appeal.getAppealContent()!=""){
            UserDao userDao=new UserDaoImpl();
            return userDao.appeal(appeal);
        }
        return new ResultInfo(false,"请填写完整信息",null);
    }
}
