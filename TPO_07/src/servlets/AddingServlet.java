package servlets;

import logic.Logic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "addingServlet", urlPatterns = "/addValues")
public class AddingServlet extends HttpServlet {

    private static final String PARAMETER_1 = "p1";
    private static final String PARAMETER_2 = "p2";
    private static final String CHARSET = "UTF-8";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }


    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(CHARSET);
        String param1 = req.getParameter(PARAMETER_1);
        String param2 = req.getParameter(PARAMETER_2);

        BigDecimal result = Logic.processParameters(param1, param2);
        resp.getWriter().write(result.toString());
    }

}
