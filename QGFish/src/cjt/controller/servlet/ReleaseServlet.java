package cjt.controller.servlet;

import cjt.service.UserBaseService;
import cjt.service.impl.UserBaseServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

/**
 * @author cjt
 */
@MultipartConfig
@WebServlet("/releaseServlet")
public class ReleaseServlet extends HttpServlet {
    /**
     * 用户上传商品图片
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取商品id
        String productId=request.getParameter("productId");
        //获取图片文件
        Part filePart = request.getPart("picture");
        UserBaseService userBaseService = new UserBaseServiceImpl();
        String filename = filePart.getSubmittedFileName();
        //传输图片文件和文件真实路径，该真实路径已在service.xml中修改过
        userBaseService.releasePicture(productId, filePart, "D:\\upload\\", filename);
        //回到使用页面
        request.getRequestDispatcher("/using.jsp").forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
