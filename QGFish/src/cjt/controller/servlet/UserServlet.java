package cjt.controller.servlet;

import cjt.model.Product;
import cjt.model.Shopping;
import cjt.model.User;
import cjt.model.dto.ResultInfo;
import cjt.service.FindService;
import cjt.service.UserService;
import cjt.service.impl.FindServiceImpl;
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
        //使得cookie在服务器下的资源都有效，解决跨域问题
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

    /**
     * 用户信息回显
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ResultInfo write(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String json = getJsonString(request);
        ObjectMapper objectMapper = new ObjectMapper();
        //将json字符串转为User对象
        User user=objectMapper.readValue(json,User.class);
        FindService findService=new FindServiceImpl();
        user=findService.findUser(user.getUserId());
        //不存入session域中，支持多用户同时使用
        return new ResultInfo(true,"回显信息",user);
    }

    /**
     * 修改个人信息
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ResultInfo update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String json = getJsonString(request);
        ObjectMapper objectMapper = new ObjectMapper();
        //将json字符串转为User对象
        User user=objectMapper.readValue(json,User.class);
        UserService userService = new UserServiceImpl();
        return userService.update(user);
    }

    /**
     * 修改密码
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
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

    /**
     * 分页模糊查询商品
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo findProductByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取当前页码
        String currentPageStr=jsonObject.getString("currentPage");
        //获取模糊商品名
        String likeProductName=jsonObject.getString("likeProductName");
        //获取模糊种类
        String likeKind=jsonObject.getString("likeKind");
        //获取选中排序
        String radio=jsonObject.getString("radio");
        UserService userService=new UserServiceImpl();
        return userService.findProductByPage(currentPageStr,likeProductName,likeKind,radio);
    }

    /**
     * 用户了解商品详情
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo read(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = getJsonString(request);
        ObjectMapper objectMapper = new ObjectMapper();
        //将json字符串转为product对象
        Product product=objectMapper.readValue(json,Product.class);
        UserService userService=new UserServiceImpl();
        return userService.read(product);
    }

    /**
     * 加入购物车
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo addShopping(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = getJsonString(request);
        ObjectMapper objectMapper = new ObjectMapper();
        //将json字符串转为User对象
        Shopping shopping =objectMapper.readValue(json,Shopping.class);
        UserService userService=new UserServiceImpl();
        return userService.addShopping(shopping);
    }

    /**
     * 用户直接购买
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo buy(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = getJsonString(request);
        ObjectMapper objectMapper = new ObjectMapper();
        //将json字符串转为Shopping对象
        Shopping shopping =objectMapper.readValue(json,Shopping.class);
        UserService userService=new UserServiceImpl();
        return userService.buy(shopping);
    }

    /**
     * 用户在购物车内提交购买信息
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo buyInShopping(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = getJsonString(request);
        ObjectMapper objectMapper = new ObjectMapper();
        //将json字符串转为User对象
        Shopping shopping =objectMapper.readValue(json,Shopping.class);
        UserService userService=new UserServiceImpl();
        return userService.buyInShopping(shopping);
    }

    /**
     * 查看购物车
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo learnShopping(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取当前页码
        String currentPageStr=jsonObject.getString("currentPage");
        //获得当前用户id
        String userIdStr=jsonObject.getString("userId");
        UserService userService=new UserServiceImpl();
        //type为1时代表查询购物车
        //type是为了封装代码，避免重复冗余代码
        return userService.findShoppingByPage(currentPageStr,userIdStr,1);
    }

    /**
     * 用户从购物车中删除该商品
     * 卖家拒绝订单
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo deleteInShopping(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取编号
        String shoppingIdStr=jsonObject.getString("shoppingId");
        UserService userService=new UserServiceImpl();
        return userService.deleteInShopping(shoppingIdStr);
    }

    /**
     * 查看我的商品
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo userProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取当前页码
        String currentPageStr=jsonObject.getString("currentPage");
        //获得当前用户id
        String userIdStr=jsonObject.getString("userId");
        UserService userService=new UserServiceImpl();
        //type为2时代表查询商品收到订单
        return userService.findShoppingByPage(currentPageStr,userIdStr,2);
    }

    /**
     * 卖家允许买家购买
     * @param request
     * @param response
     * @return
     */
    public ResultInfo allowBuy(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获得订单id
        String shoppingIdStr=jsonObject.getString("shoppingId");
        UserService userService=new UserServiceImpl();
        return userService.allowBuy(shoppingIdStr);
    }
}