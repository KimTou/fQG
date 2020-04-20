package cjt.controller;

import cjt.model.ResultInfo;
import cjt.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author cjt
 */
@WebServlet("/user/login")
public class LoginServlet extends BaseServlet {
    /**
     * 用户登录
     * @param request
     * @param response
     * @return
     */
    public ResultInfo login(HttpServletRequest request, HttpServletResponse response){
        UserService userService = new UserService();
        return userService.login(request,response);
    }
}

