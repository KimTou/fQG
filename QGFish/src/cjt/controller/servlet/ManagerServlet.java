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
     * 返回所有待审核的商品
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo check(HttpServletRequest request, HttpServletResponse response){
        String realPath = this.getServletContext().getRealPath("/upload");
        ManagerService managerService=new ManagerServiceImpl();
        return managerService.check(realPath);
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
        FindService findService=new FindServiceImpl();
        return findService.findProductByPage(currentPage,likeProductName,likeKind,radio);
    }
}
