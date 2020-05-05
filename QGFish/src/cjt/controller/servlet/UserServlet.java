package cjt.controller.servlet;

import cjt.model.Product;
import cjt.model.User;
import cjt.model.dto.ResultInfo;
import cjt.service.UserService;
import cjt.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

import static cjt.util.JsonUtil.getJsonString;

/**
 * @author cjt
 */
@MultipartConfig
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    /**
     * 使用队列实现用户顺序登陆
     */
    Queue<User> queue = new LinkedList<>();

    /**
     * 用户登录
     * @param request
     * @param response
     * @return
     */
    public ResultInfo login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = getJsonString(request);
        ObjectMapper objectMapper = new ObjectMapper();
        //将json字符串转为User对象
        User user=objectMapper.readValue(json,User.class);
        //将正在登陆的用户存进队列中去
        queue.offer(user);
        UserService userService = new UserServiceImpl();
        //队列前面的用户先登陆
        ResultInfo resultInfo = userService.login(queue.poll());
        //将登陆用户的id存储到客户端，用户名有可能会改变
        Cookie cookie=new Cookie("userId",Integer.toString(user.getUserId()));
        //使得cookie在服务器下的资源都有效
        cookie.setPath("/");
        response.addCookie(cookie);
        //如果想获取用户的详细信息，也可以以用户id或用户名作为key，将登陆的用户的对象作为value存入session域中
        return resultInfo;
    }
    /**
     * 用户注册
     * @param request
     * @param response
     * @return
     */
    public ResultInfo register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = getJsonString(request);
        ObjectMapper objectMapper = new ObjectMapper();
        //将json字符串转为User对象
        User user=objectMapper.readValue(json,User.class);
        HttpSession session = request.getSession();
        //拿到内存做出来的验证码
        String checkCode_session = (String) session.getAttribute("checkCode_session");
        UserService userService = new UserServiceImpl();
        return userService.register(user,checkCode_session);
    }

    /**
     * 卖家上传商品
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ResultInfo release(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String json = getJsonString(request);
        ObjectMapper objectMapper = new ObjectMapper();
        //将json字符串转为product对象
        Product product=objectMapper.readValue(json,Product.class);
        System.out.println(product);
        UserService userService = new UserServiceImpl();
        return userService.release(product);
    }

    public ResultInfo update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String json = getJsonString(request);
        ObjectMapper objectMapper = new ObjectMapper();
        //将json字符串转为User对象
        User user=objectMapper.readValue(json,User.class);
        UserService userService = new UserServiceImpl();
        return userService.update(user);
    }

    public ResultInfo updatePassword(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取密码
        String userId=jsonObject.getString("userId");
        //获取密码
        String oldPassword=jsonObject.getString("oldPassword");
        String newPassword1=jsonObject.getString("newPassword1");
        String newPassword2=jsonObject.getString("newPassword2");
        UserService userService = new UserServiceImpl();
        return userService.updatePassword(userId,oldPassword,newPassword1,newPassword2);
    }

    public ResultInfo findProductByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取当前页码
        String currentPageStr=jsonObject.getString("currentPage");
        //获取模糊用户名
        String likeProductName=jsonObject.getString("likeProductName");
        //获取模糊种类
        String likeKind=jsonObject.getString("likeKind");
        //获取选中排序
        String radio=jsonObject.getString("radio");
        UserService userService=new UserServiceImpl();
        return userService.findProductByPage(currentPageStr,likeProductName,likeKind,radio);
    }

    public ResultInfo read(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = getJsonString(request);
        ObjectMapper objectMapper = new ObjectMapper();
        //将json字符串转为product对象
        Product product=objectMapper.readValue(json,Product.class);
        UserService userService=new UserServiceImpl();
        return userService.read(product);
    }

}