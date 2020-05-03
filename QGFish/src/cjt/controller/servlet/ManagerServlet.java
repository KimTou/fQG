package cjt.controller.servlet;

import cjt.controller.servlet.BaseServlet;
import cjt.model.dto.ResultInfo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cjt
 */
@WebServlet("/manager/*")
public class ManagerServlet extends BaseServlet {
    public ResultInfo check(HttpServletRequest request, HttpServletResponse response) throws IOException{

        return null;
    }
}
