package cjt.controller.servlet;

import cjt.model.dto.ResultInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * @author cjt
 */
@WebServlet("/visitor/*")
public class VisitorServlet extends BaseServlet {
   public ResultInfo visitorLogin(HttpServletRequest request, HttpServletResponse response){
       Cookie cookie=new Cookie("userId","0");
       //使得cookie在服务器下的资源都有效，解决跨域问题
       cookie.setPath("/");
       response.addCookie(cookie);
       return new ResultInfo(true,"登陆成功",null);
   }

}
