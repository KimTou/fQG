package cjt.service.impl;

import cjt.dao.FindDao;
import cjt.dao.UserBaseDao;
import cjt.dao.impl.FindDaoImpl;
import cjt.dao.impl.UserBaseDaoImpl;
import cjt.model.*;
import cjt.model.dto.ResultInfo;
import cjt.service.FindService;
import cjt.service.UserBaseService;
import cjt.util.SendMail;

import javax.servlet.http.Part;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static cjt.util.Md5Util.getMD5String;

/**
 * @author cjt
 * 用户基础功能业务（个人信息，商品信息）
 */
public class UserBaseServiceImpl implements UserBaseService {

    /**
     * 用户登陆
     *
     * @param user
     * @return
     */
    @Override
    public ResultInfo login(User user) {
        //判断用户输入是否为空
        if (user.getUserName().length() != 0 && user.getPassword().length() != 0) {
            //将密码md5加密对应数据库的已加密的密码
            String password = getMD5String(user.getPassword());
            user.setPassword(password);
            UserBaseDao userBaseDao = new UserBaseDaoImpl();
            //传入用户输入信息与存入数据库的信息进行校验
            return userBaseDao.login(user);
        } else {
            return new ResultInfo(false, "请填写完整信息", null);
        }
    }

    /**
     * 用户注册
     *
     * @param user
     * @param checkCode_session
     * @return
     */
    @Override
    public ResultInfo register(User user, String checkCode_session) {
        //不区分大小写校验验证码
        if (!checkCode_session.equalsIgnoreCase(user.getCheckCode())) {
            return new ResultInfo(false, "验证码错误", null);
        }
        //判断是否输入信息为空
        if (user.getUserName().length() == 0 || user.getPassword().length() == 0 || user.getEmail().length() == 0
                || user.getPhone().length() == 0 || user.getAddress().length() == 0) {
            return new ResultInfo(false, "请完整填写信息", null);
        }
        //设置正则表达式校验邮箱格式和手机号码格式
        String regexEmail = "\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";
        String regexPhone = "1[3578][0-9]{9}";
        //尽量减少if-else嵌套
        if (!user.getEmail().matches(regexEmail)) {
            return new ResultInfo(false, "邮箱格式不合法", null);
        }
        if (!user.getPhone().matches(regexPhone)) {
            return new ResultInfo(false, "电话号码格式不合法", null);
        }
        FindDao findDao = new FindDaoImpl();
        //验证是否有信息被注册过，保证信息的唯一性
        if (findDao.findUserIsExit(user.getUserName(), 1)) {
            return new ResultInfo(false, "该用户名已被注册", null);
        }
        if (findDao.findUserIsExit(user.getEmail(), 2)) {
            return new ResultInfo(false, "该邮箱已被注册过", null);
        }
        if (findDao.findUserIsExit(user.getPhone(), 3)) {
            return new ResultInfo(false, "该电话号码已被注册过", null);
        }
        //将密码md5加密
        String password = getMD5String(user.getPassword());
        //封装用户信息
        user.setPassword(password);
        //初始化用户信息
        user.setCondition("正常");
        //初始经验值为100，每100点经验会员等级加1
        user.setExp(100);
        UserBaseDao userBaseDao = new UserBaseDaoImpl();
        //将用户填写的信息存入数据库
        return userBaseDao.register(user);
    }

    /**
     * 卖家上传商品
     *
     * @param product
     * @return
     */
    @Override
    public ResultInfo release(Product product) {
        FindService findService = new FindServiceImpl();
        //通过商品卖家id查找用户信息
        User user = findService.findUser(product.getProductSeller());
        //首先判断用户是否有权限售卖商品
        if ("禁止售卖".equals(user.getCondition())) {
            //如果被禁售则直接携带禁售理由返回客户端
            return new ResultInfo(false, "你已被禁止售卖!\n原因：" + user.getLabel(), null);
        }
        //判断用户输入是否合法
        if (product.getProductName().length() == 0 || product.getProductKind().length() == 0) {
            return new ResultInfo(false, "请填写完整商品信息", null);
        }
        //限制价格和数量
        if (product.getProductPrice() < 0 || product.getProductPrice() > 9999999 || product.getProductAmount() < 0 || product.getProductAmount() > 9999999) {
            return new ResultInfo(false, "请输入合理的商品价格和商品数量（0~9999999）", null);
        }
        //为商品初始化出货量
        product.setProductSold(0);
        //为商品初始化得分情况
        product.setProductScore(5);
        product.setProductScoreTime(1);
        product.setProductStarLevel(5.0);
        //设置商品评论为" "，防止用户看到商品的评论为null
        product.setProductComment(" ");
        //为商品初始化状态
        product.setProductCondition("待审核");
        UserBaseDao userBaseDao = new UserBaseDaoImpl();
        //将用户填写的信息存入数据库
        return userBaseDao.release(product);
    }

    /**
     * 用户修改个人信息
     *
     * @param user
     * @return
     */
    @Override
    public ResultInfo update(User user) {
        //判断是否是正常方式的访问
        if (user.getUserId() < 1) {
            return new ResultInfo(false, "修改失败", null);
        }
        //判断是否有空的地方
        if (user.getUserName().length() == 0 || user.getEmail().length() == 0
                || user.getPhone().length() == 0 || user.getAddress().length() == 0) {
            return new ResultInfo(false, "请完整填写信息", null);
        }
        //设置正则表达式校验邮箱格式和手机号码格式
        String regexEmail = "\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";
        String regexPhone = "1[3578][0-9]{9}";
        //只有合法才能修改
        if (!user.getEmail().matches(regexEmail)) {
            return new ResultInfo(false, "邮箱格式不合法", null);
        }
        if (!user.getPhone().matches(regexPhone)) {
            return new ResultInfo(false, "手机号码格式不合法", null);
        }
        UserBaseDao userBaseDao = new UserBaseDaoImpl();
        //将用户填写的信息存入数据库
        return userBaseDao.update(user);
    }

    /**
     * 用户修改密码
     *
     * @param userId
     * @param oldPassword
     * @param newPassword1
     * @param newPassword2
     * @return
     */
    @Override
    public ResultInfo updatePassword(int userId, String oldPassword, String newPassword1, String newPassword2) {
        //判断是否是正常方式的访问
        if (userId < 1) {
            return new ResultInfo(false, "修改失败", null);
        }
        //先判断密码输入栏是否有空的
        if (oldPassword.length() == 0 || newPassword1.length() == 0 || newPassword2.length() == 0) {
            return new ResultInfo(false, "密码不能为空", null);
        }
        //再判断两次输入的新密码是否一致
        if (!newPassword1.equals(newPassword2)) {
            return new ResultInfo(false, "两次输入的新密码不一致", null);
        }
        //查找用户原本密码
        FindService findService = new FindServiceImpl();
        User user = findService.findUser(userId);
        //判断用户输入的旧密码是否正确
        String password1 = getMD5String(oldPassword);
        if (user.getPassword().equals(password1)) {
            UserBaseDao userBaseDao = new UserBaseDaoImpl();
            //正确则修改密码
            String password2 = getMD5String(newPassword2);
            user.setPassword(password2);
            return userBaseDao.updatePassword(user);
        } else {
            return new ResultInfo(false, "请输入正确的原先密码", null);
        }
    }

    /**
     * 用户忘记密码，找回密码
     *
     * @param email
     * @return
     */
    @Override
    public ResultInfo findBackPassword(String email) {
        if (email.length() == 0) {
            return new ResultInfo(false, "请填写邮箱信息", null);
        }
        FindDao findDao = new FindDaoImpl();
        //若正确，返回封装了用户id的对象 ；若错误，返回null
        User user = findDao.findUser(email);
        //首先判断用户邮箱是否输入正确
        if (user == null) {
            return new ResultInfo(false, "邮箱错误", null);
        }
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
        //重置用户密码,将密码md5加密
        user.setPassword(getMD5String(newPassword));
        UserBaseDao userBaseDao = new UserBaseDaoImpl();
        //先判断是否重置成功，成功后再发给用户邮件，以免误导用户
        if (userBaseDao.updatePassword(user).isStatus()) {
            String mailContent = "您的密码已重置为：" + newPassword + "（请根据重置的密码进行登陆并尽快修改密码。感谢您的使用！）";
            //实例化一个发送邮件的对象
            SendMail mySendMail = new SendMail();
            //设置收件人和消息内容
            mySendMail.sendMail(email, mailContent);
            return new ResultInfo(true, "操作成功，快去您的邮箱看看吧", mailContent);
        } else {
            return new ResultInfo(false, "重置失败", null);
        }
    }

    /**
     * 卖家上传商品图片
     *
     * @param productId
     * @param filePart
     * @param realPath
     * @return
     * @throws IOException
     */
    @Override
    public boolean releasePicture(String productId, Part filePart, String realPath, String filename) throws IOException {
        //若添加了图片，则存储
        if (filePart != null && filename != null) {
            //以当前时间为图片命名，防止重复
            String str = (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(new Date());
            //取文件后缀
            String[] s = filename.split("\\.");
            //防止数组越界
            if (s.length > 1) {
                //拼接文件名
                String fileName = str + "." + s[1];
                //将图片存入本地，路径为realPath
                filePart.write(realPath + "\\" + fileName);
                UserBaseDao userBaseDao = new UserBaseDaoImpl();
                return userBaseDao.uploadPicture(Integer.parseInt(productId), fileName);
            } else {
                //添加失败，也附加一张无图片
                UserBaseDao userBaseDao = new UserBaseDaoImpl();
                return userBaseDao.uploadPicture(Integer.parseInt(productId), "noPic.jpg");
            }
        } else {
            //当没有添加图片，则附一张无图片
            UserBaseDao userBaseDao = new UserBaseDaoImpl();
            return userBaseDao.uploadPicture(Integer.parseInt(productId), "noPic.jpg");
        }
    }

    /**
     * 了解商品详情
     *
     * @param product
     * @return
     */
    @Override
    public ResultInfo read(Product product) {
        if (product.getProductId() > 0) {
            FindService findService = new FindServiceImpl();
            //通过商品id查找完整的product信息
            product = findService.findProduct(product.getProductId());
            //将完整的product对象存入ResultInfo
            if (product != null) {
                return new ResultInfo(true, "查询成功", product);
            } else {
                return new ResultInfo(false, "数据库连接失败", null);
            }
        } else {
            return new ResultInfo(false, "查询失败", null);
        }
    }

    /**
     * 实现推荐商品的队列缓存
     */
    private static Map<Integer, ConcurrentLinkedQueue<Product>> queueMap = new ConcurrentHashMap<Integer, ConcurrentLinkedQueue<Product>>();
    //静态变量，生命周期与类相同

    /**
     * 分页模糊查找商品
     * @param userId
     * @param currentPage
     * @param likeProductName
     * @param likeKind
     * @param radio
     * @return
     */
    @Override
    public ResultInfo findProductByPage(int userId, int currentPage, String likeProductName, String likeKind, String radio) {
        if (currentPage < 1 && userId < 1) {
            return new ResultInfo(false, "查询失败", null);
        }
        //创建分页对象
        Page<Product> page = new Page<>();
        int rows = 4;
        //设置参数
        page.setCurrentPage(currentPage);
        page.setRows(rows);
        //调用dao查询商品总记录数
        FindDao findDao = new FindDaoImpl();
        int totalCount = findDao.findProductTotalCount(likeProductName, likeKind);
        page.setTotalCount(totalCount);
        //计算开始的记录索引
        int start = (currentPage - 1) * rows;
        //计算总页码
        int totalPage = (totalCount % rows == 0) ? (totalCount / rows) : (totalCount / rows) + 1;
        page.setTotalPage(totalPage);
        //返回每页的数据集合
        List<Product> list = findDao.findProductByPage(start, rows, likeProductName, likeKind, radio);
        UserBaseDao userBaseDao = new UserBaseDaoImpl();
        ConcurrentLinkedQueue<Product> queue = null;
        //查找队列集合中是否存在属于请求用户id对应的队列
        for (int id : queueMap.keySet()) {
            if (id == userId) {
                //存在则取出该队列
                queue = queueMap.get(id);
                break;
            }
        }
        if (queue == null) {
            //不存在则创建一个队列，并存入队列集合中，key为用户id，value为该用户的缓存队列
            queue = new ConcurrentLinkedQueue<>();
            queueMap.put(userId, queue);
        }
        //如果队列为空，则添加缓存
        if (queue.isEmpty()) {
            //获取用户感兴趣的商品种类
            List<String> listLoveKind = userBaseDao.findUserLoveKind(userId);
            for (String loveKind : listLoveKind) {
                //获取每一种类的商品
                List<Product> listLove = userBaseDao.findUserLove(loveKind);
                for (Product product : listLove) {
                    //不推荐重复商品
                    if (!findDao.findShopping(product.getProductId(), userId)) {
                        queue.offer(product);
                    }
                    //以免队列过长，不超过10个
                    if (queue.size() > 10) {
                        break;
                    }
                }
                if (queue.size() > 10) {
                    break;
                }
            }
        }
        //如果队列还是为空，说明用户购物车和订单里没有商品，那么为其推荐最便宜实惠的
        if (queue.isEmpty()) {
            List<Product> listCheap = userBaseDao.findCheapProduct();
            for (Product product : listCheap) {
                queue.offer(product);
            }
        }
        //再次确保队列不为空
        if (queue.size() != 0) {
            //为每页的商品数据再加上一个推荐商品
            list.add(queue.poll());
        }
        page.setList(list);
        if (list != null) {
            return new ResultInfo(true, "分页查询完毕", page);
        } else {
            return new ResultInfo(false, "查询结果为空", page);
        }
    }

}
