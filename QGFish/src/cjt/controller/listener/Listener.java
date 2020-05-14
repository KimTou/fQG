package cjt.controller.listener;

import cjt.util.DbUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static cjt.util.DbUtil.getCon;


/**
 * @author cjt
 */
@WebListener
public class Listener implements ServletContextListener {

    /**
     * 连接数据库
     */
    private Connection con=null;
    private PreparedStatement stmt=null;
    private ResultSet rs=null;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //服务器启动时自动连接mysql数据库
        try {
            con=getCon();
        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //服务器关闭时关闭mysql数据库连接
        try{
            DbUtil.close(rs,stmt, con);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

}
//    @Override
//    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
//        HttpServletRequest request = (HttpServletRequest)servletRequestEvent.getServletRequest();
//        System.out.println("----发向" + request.getRequestURI() + "请求被销毁----");
//    }
//
//    @Override
//    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
//        HttpServletRequest request = (HttpServletRequest)servletRequestEvent.getServletRequest();
//        System.out.println("----发向" + request.getRequestURI() + "请求被初始化----");
//    }
//}

