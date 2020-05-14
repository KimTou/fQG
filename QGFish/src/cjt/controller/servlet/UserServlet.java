package cjt.controller.servlet;

import cjt.model.Appeal;
import cjt.model.Product;
import cjt.model.Shopping;
import cjt.model.User;
import cjt.model.dto.ResultInfo;
import cjt.service.FindService;
import cjt.service.ManagerService;
import cjt.service.UserAdvancedService;
import cjt.service.UserBaseService;
import cjt.service.impl.FindServiceImpl;
import cjt.service.impl.ManagerServiceImpl;
import cjt.service.impl.UserAdvancedServiceImpl;
import cjt.service.impl.UserBaseServiceImpl;
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
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    /**
     * 使用队列实现用户顺序登陆
     */
    private static Queue<User> queue = new LinkedList<>();

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
        User user = objectMapper.readValue(json,User.class);
        //将正在登陆的用户存进队列中去
        queue.offer(user);
        UserBaseService userBaseService = new UserBaseServiceImpl();
        //队列前面的用户先登陆
        ResultInfo resultInfo = userBaseService.login(queue.poll());
        //将登陆用户的id存储到客户端
        Cookie cookie = new Cookie("userId",Integer.toString(user.getUserId()));
        if("true".equals(user.getLabel())){
            //免登录一天
            //cookie.setMaxAge(60*60*12);
            cookie.setMaxAge(60);
        }
        //使得cookie在服务器下的资源都有效，解决跨域问题
        cookie.setPath("/");
        //发送cookie
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
        HttpSession session=request.getSession();
        //拿到内存做出来的验证码
        String checkCode_session = (String) session.getAttribute("checkCode_session");
        UserBaseService userBaseService = new UserBaseServiceImpl();
        //注册成功则清除session
        ResultInfo resultInfo= userBaseService.register(user,checkCode_session);
        if(resultInfo.isStatus()==true){
            session.removeAttribute("checkCode_session");
        }
        return resultInfo;
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
        UserBaseService userBaseService = new UserBaseServiceImpl();
        return userBaseService.release(product);
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
        return new ResultInfo(true,"回显成功",user);
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
        UserBaseService userBaseService = new UserBaseServiceImpl();
        return userBaseService.update(user);
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
        int userId=jsonObject.getInt("userId");
        //获取密码
        String oldPassword=jsonObject.getString("oldPassword");
        String newPassword1=jsonObject.getString("newPassword1");
        String newPassword2=jsonObject.getString("newPassword2");
        UserBaseService userBaseService = new UserBaseServiceImpl();
        return userBaseService.updatePassword(userId,oldPassword,newPassword1,newPassword2);
    }

    /**
     * 用户忘记密码，请求找回密码
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo findBackPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取用户邮箱
        String email=jsonObject.getString("email");
        UserBaseService userBaseService =new UserBaseServiceImpl();
        return userBaseService.findBackPassword(email);
    }

    /**
     * 游客分页模糊查询商品
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo visitorFindProductByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取当前页码
        int currentPage=jsonObject.getInt("currentPage");
        //获取模糊商品名
        String likeProductName=jsonObject.getString("likeProductName");
        //获取模糊种类
        String likeKind=jsonObject.getString("likeKind");
        //获取选中排序
        String radio=jsonObject.getString("radio");
        ManagerService managerService=new ManagerServiceImpl();
        return managerService.findProductByPage(currentPage,likeProductName,likeKind,radio);
    }

    /**
     * 用户分页模糊查询商品
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
        //获取用户id
        int userId=jsonObject.getInt("userId");
        //获取当前页码
        int currentPage=jsonObject.getInt("currentPage");
        //获取模糊商品名
        String likeProductName=jsonObject.getString("likeProductName");
        //获取模糊种类
        String likeKind=jsonObject.getString("likeKind");
        //获取选中排序
        String radio=jsonObject.getString("radio");
        UserBaseService userBaseService=new UserBaseServiceImpl();
        return userBaseService.findProductByPage(userId,currentPage,likeProductName,likeKind,radio);
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
        UserBaseService userBaseService =new UserBaseServiceImpl();
        return userBaseService.read(product);
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
        //将json字符串转为Shopping对象
        Shopping shopping =objectMapper.readValue(json,Shopping.class);
        UserAdvancedService userAdvancedService=new UserAdvancedServiceImpl();
        return userAdvancedService.addShopping(shopping);
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
        UserAdvancedService userAdvancedService=new UserAdvancedServiceImpl();
        return userAdvancedService.buy(shopping);
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
        UserAdvancedService userAdvancedService=new UserAdvancedServiceImpl();
        return userAdvancedService.buyInShopping(shopping);
    }

    /**
     * 查看购物车，分页查询
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
        int currentPage=jsonObject.getInt("currentPage");
        //获得当前用户id
        int userId=jsonObject.getInt("userId");
        UserAdvancedService userAdvancedService=new UserAdvancedServiceImpl();
        //type为1时代表查询购物车
        //type是为了封装代码，避免重复冗余代码
        return userAdvancedService.findShoppingByPage(currentPage,userId,1);
    }

    /**
     * 用户从购物车中删除该商品
     * 卖家拒绝订单
     * 买家取消订单
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
        int shoppingId=jsonObject.getInt("shoppingId");
        UserAdvancedService userAdvancedService=new UserAdvancedServiceImpl();
        return userAdvancedService.deleteInShopping(shoppingId);
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
        int currentPage=jsonObject.getInt("currentPage");
        //获得当前用户id
        int userId=jsonObject.getInt("userId");
        UserAdvancedService userAdvancedService=new UserAdvancedServiceImpl();
        //type为2时代表查询商品收到订单
        return userAdvancedService.findShoppingByPage(currentPage,userId,2);
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
        int shoppingId=jsonObject.getInt("shoppingId");
        UserAdvancedService userAdvancedService=new UserAdvancedServiceImpl();
        return userAdvancedService.allowBuy(shoppingId);
    }

    /**
     * 用户分页查看订单，即卖家已发货的
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo userOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取当前页码
        int currentPage=jsonObject.getInt("currentPage");
        //获得当前用户id
        int userId=jsonObject.getInt("userId");
        UserAdvancedService userAdvancedService=new UserAdvancedServiceImpl();
        //type为3时代表查询订单（即卖家已发货的）
        return userAdvancedService.findShoppingByPage(currentPage,userId,3);
    }

    /**
     * 用户确认订单
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo comfirmOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取订单id
        int shoppingId=jsonObject.getInt("shoppingId");
        //获得评分
        int score=jsonObject.getInt("score");
        //获取评论
        String comment=jsonObject.getString("comment");
        UserAdvancedService userAdvancedService=new UserAdvancedServiceImpl();
        return userAdvancedService.evaluate(shoppingId,score,comment);
    }

    /**
     * 卖家查询自己发布的商品
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo findMyProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取当前页码
        int currentPage=jsonObject.getInt("currentPage");
        //获得当前用户id
        int userId=jsonObject.getInt("userId");
        UserAdvancedService userAdvancedService=new UserAdvancedServiceImpl();
        //type为3时代表查询订单（即卖家已发货的）
        return userAdvancedService.findMyProduct(currentPage,userId);
    }

    /**
     * 卖家回复自己商品的评论
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo reply(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取当前页码
        String comment=jsonObject.getString("comment");
        //获得当前商品id
        int productId=jsonObject.getInt("productId");
        UserAdvancedService userAdvancedService=new UserAdvancedServiceImpl();
        //type为3时代表查询订单（即卖家已发货的）
        return userAdvancedService.reply(comment,productId);
    }

    /**
     * 卖家删除自己发布的商品
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo deleteMyProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获得当前商品id
        int productId=jsonObject.getInt("productId");
        //调用管理员的方法，因为此时对待自己的商品也可以把自己当成一个小小管理员
        ManagerService managerService=new ManagerServiceImpl();
        return managerService.ban(productId);
    }

    /**
     * 卖家修改订单时订单信息的回显
     * @param request
     * @param response
     * @return
     */
    public ResultInfo writeShopping(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获得当前商品id
        int shoppingId=jsonObject.getInt("shoppingId");
        FindService findService=new FindServiceImpl();
        //通过find类查询订单完整信息
        Shopping shopping=findService.findShopping(shoppingId);
        return new ResultInfo(true,"回显成功",shopping);
    }

    /**
     * 卖家修改订单信息
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo updateShopping(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = getJsonString(request);
        ObjectMapper objectMapper = new ObjectMapper();
        //将json字符串转为Shopping对象
        Shopping shopping =objectMapper.readValue(json,Shopping.class);
        UserAdvancedService userAdvancedService=new UserAdvancedServiceImpl();
        return userAdvancedService.updateShopping(shopping);
    }

    /**
     * 用户提交申诉信息
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo appeal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = getJsonString(request);
        ObjectMapper objectMapper = new ObjectMapper();
        //将json字符串转为Appeal对象
        Appeal appeal =objectMapper.readValue(json, Appeal.class);
        UserAdvancedService userAdvancedService=new UserAdvancedServiceImpl();
        return userAdvancedService.appeal(appeal);
    }
}