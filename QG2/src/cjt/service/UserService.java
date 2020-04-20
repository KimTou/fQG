package cjt.service;

import cjt.dao.UserDao;
import cjt.model.ResultInfo;
import cjt.model.User;

/**
 * @author cjt
 */
public class UserService {

    User user = null;
    UserDao userDao = null;

    /**
     * 用户登录
     * @param userName
     * @param password
     * @param checkCode
     * @param checkCode_session
     * @return
     */
    public ResultInfo login(String userName,String password,String checkCode,String checkCode_session) {
        //先检查验证码是否正确
        //正确再与数据库连接
        if(checkCode_session.equalsIgnoreCase(checkCode)) {
            //判断用户输入是否合法
            if(userName.length()!=0&&password.length()!=0) {
                user = new User();
                user.setUserName(userName);
                user.setPassword(password);
                userDao = new UserDao();
                //传入用户信息与数据库进行校验
                return userDao.login(user);
            }
            else {
                return new ResultInfo(false,"用户名和密码不能为空，请重新输入");
            }
        }
        //不正确则直接结束
        else {
            return new ResultInfo(false,"验证码错误");
        }
    }

    /**
     * 用户注册
     * @param userName
     * @param password
     * @param email
     * @param checkCode
     * @param checkCode_session
     * @return
     */
    public ResultInfo register(String userName,String password,String email,String checkCode,String checkCode_session) {
        if(checkCode_session.equalsIgnoreCase(checkCode)) {
            //判断是否输入信息为空
            if(userName.length()!=0&&password.length()!=0&&email.length()!=0) {
                //设置正则表达式校验邮箱格式
                String regex="\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";
                if(email.matches(regex)) {
                    user = new User();
                    user.setUserName(userName);
                    user.setPassword(password);
                    user.setEmail(email);
                    userDao = new UserDao();
                    //将用户填写的信息存入数据库
                    return userDao.register(user);
                }
                else {
                    return new ResultInfo(false,"邮箱格式不合法");
                }
            }
            else {
                return new ResultInfo(false,"用户名密码和邮箱不能为空，请重新输入");
            }
        }
        else {
            return new ResultInfo(false,"验证码错误");
        }
    }

}
