package cjt.service;

import cjt.dao.UserDao;
import cjt.model.ResultInfo;
import cjt.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author cjt
 */
public class UserService {

    User user = null;
    UserDao userDao = null;

    /**
     * 用户登录
     * @param request
     * @param response
     * @return
     */
    public ResultInfo login(HttpServletRequest request, HttpServletResponse response) {
        //先检查验证码是否正确
        //正确再与数据库连接
        if(checkCode(request,response)) {
            user = new User();
            user.setUserName(request.getParameter("username"));
            user.setPassword(request.getParameter("password"));
            userDao = new UserDao();
            //传入用户信息与数据库进行校验
            return userDao.login(user);
        }
        //不正确则直接结束
        else {
            return new ResultInfo(false,"验证码错误");
        }
    }

    /**
     * 用户注册
     * @param request
     * @param response
     * @return
     */
    public ResultInfo register(HttpServletRequest request, HttpServletResponse response) {
        if(checkCode(request,response)) {
            user = new User();
            user.setUserName(request.getParameter("username"));
            user.setPassword(request.getParameter("password"));
            user.setEmail(request.getParameter("email"));
            userDao = new UserDao();
            //将用户填写的信息存入数据库
            return userDao.register(user);
        }
        else {
            return new ResultInfo(false,"验证码错误");
        }
    }

    /**
     * 判断验证码是否正确
     * @param request
     * @param response
     * @return
     */
    public boolean checkCode(HttpServletRequest request, HttpServletResponse response) {
        //拿到用户输入的验证码
        String checkCode = request.getParameter("checkCode");
        HttpSession session = request.getSession();
        //拿到做出来的验证码
        String checkCode_session = (String) session.getAttribute("checkCode_session");
        //忽略大小写，两者相比较，用户输入的在后，防止空指针异常
        if (checkCode_session.equalsIgnoreCase(checkCode)) {
            return true;
        } else {
            return false;
        }
    }
}
