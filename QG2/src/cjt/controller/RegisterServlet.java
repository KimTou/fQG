package cjt.controller;

import cjt.model.ResultInfo;
import cjt.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cjt
 */
@WebServlet("/user/register")
public class RegisterServlet extends BaseServlet {
    /**
     * 用户注册
     * @param request
     * @param response
     * @return
     */
    public ResultInfo register(HttpServletRequest request, HttpServletResponse response){
        UserService userService=new UserService();
        return userService.register(request,response);
    }
}
