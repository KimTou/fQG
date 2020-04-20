package cjt.controller;

import cjt.model.ResultInfo;
import cjt.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author cjt
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    /**
     * 用户登录
     * @param request
     * @param response
     * @return
     */
    public ResultInfo login(HttpServletRequest request, HttpServletResponse response){
        //拿到用户输入的验证码
        String checkCode = request.getParameter("checkCode");
        HttpSession session = request.getSession();
        //拿到内存做出来的验证码
        String checkCode_session = (String) session.getAttribute("checkCode_session");
        UserService userService = new UserService();
        return userService.login(request.getParameter("username"),request.getParameter("password"),
                checkCode,checkCode_session);
    }
    /**
     * 用户注册
     * @param request
     * @param response
     * @return
     */
    public ResultInfo register(HttpServletRequest request, HttpServletResponse response) {
        //拿到用户输入的验证码
        String checkCode = request.getParameter("checkCode");
        HttpSession session = request.getSession();
        //拿到内存做出来的验证码
        String checkCode_session = (String) session.getAttribute("checkCode_session");
        UserService userService = new UserService();
        return userService.register(request.getParameter("username"), request.getParameter("password"),
                request.getParameter("email"),checkCode,checkCode_session);
    }

}



//删了有关映射方法的某个类，被忘了看看out包里有没有删掉