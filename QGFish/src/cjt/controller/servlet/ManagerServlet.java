package cjt.controller.servlet;

import cjt.controller.servlet.BaseServlet;
import cjt.model.Page;
import cjt.model.Product;
import cjt.model.dto.ResultInfo;
import cjt.service.ManagerService;
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
     * 返回所有用户
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ResultInfo person(HttpServletRequest request, HttpServletResponse response){
        ManagerService managerService=new ManagerServiceImpl();
        return managerService.findAllUser();
    }

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
        String productId=jsonObject.getString("productId");
        ManagerService managerService=new ManagerServiceImpl();
        return managerService.release(productId);
    }

    /**
     * 禁止商品发布
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
        String productId=jsonObject.getString("productId");
        ManagerService managerService=new ManagerServiceImpl();
        return managerService.ban(productId);
    }

    public ResultInfo findUserByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得json字符串
        String json = getJsonString(request);
        //获取json字符串键值对
        JSONObject jsonObject = JSONObject.fromObject(json);
        //获取当前页码
        String currentPageStr=jsonObject.getString("currentPage");
        ManagerService managerService=new ManagerServiceImpl();
        return managerService.findUserByPage(currentPageStr);
    }
}
