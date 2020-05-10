package cjt.controller.servlet;

import cjt.service.UserAdvancedService;
import cjt.service.UserBaseService;
import cjt.service.impl.UserAdvancedServiceImpl;
import cjt.service.impl.UserBaseServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/downLoadServlet")
public class DownLoadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取订单id
        String shoppingIdStr=request.getParameter("shoppingId");
        UserAdvancedService userAdvancedService=new UserAdvancedServiceImpl();
        String path= userAdvancedService.downLoad(shoppingIdStr);
        FileInputStream fis=new FileInputStream(path);
//        response.setHeader("content-type","text/html");
        response.setHeader("Content-Disposition","attachment;filename=order"+shoppingIdStr+".txt");
        ServletOutputStream sos=response.getOutputStream();
        byte[] buff=new byte[1024*8];
        int len=0;
        while((len=fis.read(buff))!=-1){
            sos.write(buff,0,len);
        }
        fis.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
