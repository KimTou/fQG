package cjt.service.impl;

import cjt.dao.UserDao;
import cjt.dao.impl.UserDaoImpl;
import cjt.model.Product;
import cjt.model.User;
import cjt.model.dto.ResultInfo;
import cjt.service.FindService;
import cjt.service.UserService;

import javax.servlet.http.Part;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                    && user.getPhone().length() != 0 && user.getRealName().length() != 0) {
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
                && user.getPhone().length() != 0 && user.getRealName().length() != 0) {
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
}
