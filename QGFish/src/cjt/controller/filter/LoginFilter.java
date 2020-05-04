package cjt.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author cjt
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException, IOException {
        //强制转换
        HttpServletRequest request= (HttpServletRequest) req;
        //获取资源访问路径
        String uri=request.getRequestURI();
        //判断是否包含登陆资源相关的资源路径，要注意排除css/js/图片/验证码等资源
        if(uri.contains("/login.jsp")||uri.contains("/checkCodeServlet")||uri.contains("/user/login")||uri.contains("/user/register")||uri.contains("/index.jsp")||uri.contains("/js/")||uri.contains("/css/")){
            //包含，证明用户想登陆，放行
            chain.doFilter(req, resp);
        }
        else{
            //不包含，需要验证用户是否登陆
            //从session获取user
            Object user=request.getSession().getAttribute("user");
            if(user!=null){
                chain.doFilter(req,resp);
            }
            else{
                //没有登陆，则跳转登陆界面
                request.getRequestDispatcher("/login.jsp").forward(request,resp);
            }
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void destroy() {
    }
}
