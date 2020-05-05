package cjt.controller.servlet;

import cjt.model.User;
import cjt.service.FindService;
import cjt.service.impl.FindServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cjt
 */
@WebServlet("/findServlet")
public class FindServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 查找用户信息
         * @param request
         * @param response
         * @return
         * @throws IOException
         * @throws ServletException
         */
            //获取用户id
            String userId = request.getParameter("userId");
            FindService findService = new FindServiceImpl();
            //通过用户id查找用户位置信息
            User user = findService.findUser(userId);
            //将完整的用户信息存储到修改页面，以便回显用户信息
            request.setAttribute("user",user);
            //转发到修改页面
            request.getRequestDispatcher("/update.jsp").forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
