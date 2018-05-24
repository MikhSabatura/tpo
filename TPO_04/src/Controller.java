import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TPO_04_controller", urlPatterns = "/addValues")
public class Controller extends HttpServlet {

    private static final String PARAMETER_1 = "p1";
    private static final String PARAMETER_2 = "p2";
    private static final String MODEL = "model";

    private static final String VIEW_SERVLET = "/addValues/view";
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

        Model model = Logic.processParameters(param1, param2);
        req.setAttribute(MODEL, model);
        RequestDispatcher dispatcher = req.getRequestDispatcher(VIEW_SERVLET);
        dispatcher.forward(req, resp);
    }

}
