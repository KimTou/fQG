package cjt.controller.servlet;

import cjt.controller.servlet.BaseServlet;
import cjt.model.Page;
import cjt.model.Product;
import cjt.model.dto.ResultInfo;
import cjt.service.FindService;
import cjt.service.ManagerService;
import cjt.service.impl.FindServiceImpl;
import cjt.service.impl.ManagerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cjt.util.JsonUtil.getJsonString;

/**
 * @author cjt
 */
@WebServlet("/manager/*")
public class ManagerServlet extends BaseServlet {

    /**
     * 分页返回所有待审核的商品
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo check(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取当前页码
        int currentPage=jsonObject.getInt("currentPage");
        ManagerService managerService=new ManagerServiceImpl();
        return managerService.check(currentPage);
    }

    /**
     * 允许商品发布
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo release(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取商品id
        int productId=jsonObject.getInt("productId");
        ManagerService managerService=new ManagerServiceImpl();
        return managerService.release(productId);
    }

    /**
     * 禁止商品发布
     * 商品下架
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo ban(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取商品id
        int productId=jsonObject.getInt("productId");
        ManagerService managerService=new ManagerServiceImpl();
        return managerService.ban(productId);
    }

    /**
     * 分页查询用户
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo findUserByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取当前页码
        int currentPage=jsonObject.getInt("currentPage");
        ManagerService managerService=new ManagerServiceImpl();
        return managerService.findUserByPage(currentPage);
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
     * 恢复用户售卖商品功能
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo recover(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取用户id
        int userId=jsonObject.getInt("userId");
        ManagerService managerService=new ManagerServiceImpl();
        return managerService.recover(userId);
    }

    /**
     * 禁止用户售卖商品
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo banUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取用户id
        int userId=jsonObject.getInt("userId");
        //获取禁用理由
        String label=jsonObject.getString("label");
        ManagerService managerService=new ManagerServiceImpl();
        return managerService.banUser(userId,label);
    }

    /**
     * 获取申诉信息
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo getAppeal(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取当前页码
        int currentPage=jsonObject.getInt("currentPage");
        ManagerService managerService=new ManagerServiceImpl();
        return managerService.getAppeal(currentPage);
    }

    /**
     * 对申诉信息标记已阅
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo read(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取申诉id
        int id=jsonObject.getInt("id");
        ManagerService managerService=new ManagerServiceImpl();
        return managerService.read(id);
    }
}
