package cjt.controller.servlet;

import cjt.service.UserService;
import cjt.service.impl.UserServiceImpl;

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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId=request.getParameter("productId");
        String userId=request.getParameter("userId");
        Part filePart = request.getPart("picture");
        //防止数组越界
        if(filePart!=null) {
            String filename = filePart.getSubmittedFileName();
            String realPath = this.getServletContext().getRealPath("/upload");
            UserService userService = new UserServiceImpl();
            //传输文件和文件真实路径
            userService.releasePicture(productId, filePart, realPath, filename);
        }
        //回到使用页面
        request.getRequestDispatcher("/using.jsp?userId="+userId).forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
