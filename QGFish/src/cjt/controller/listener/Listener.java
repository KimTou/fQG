package cjt.controller.listener;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author cjt
 */
@WebListener
public class Listener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        ServletContext application = session.getServletContext();
        if (application.getAttribute("userNum") == null) {
            application.setAttribute("userNum", 1);
        } else {
            int userNum = (int) application.getAttribute("userNum") + 1;
            application.setAttribute("userNum", userNum);
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        ServletContext application = session.getServletContext();
        if (application.getAttribute("userNum") != null) {
            int userNum = (int) application.getAttribute("userNum") - 1;
            application.setAttribute("userNum", userNum);
        }
    }
}

//    /**
//     * 连接数据库
//     */
//    private Connection con=null;
//    private PreparedStatement stmt=null;
//    private ResultSet rs=null;
//
//    @Override
//    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        //服务器启动时自动连接mysql数据库
//        try {
//            con=getCon();
//        }catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent servletContextEvent) {
//        //服务器关闭时关闭mysql数据库连接
//        try{
//            DbUtil.close(rs,stmt, con);
//        } catch(SQLException e){
//            e.printStackTrace();
//        }
//    }
//
//}
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

