import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "TPO_03", value = "/addValues")
public class FormServlet extends HttpServlet {

    private static final String NUMBER_STRING = "(0|-?[1-9]\\d*)(\\.\\d+)?";
    private static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_STRING);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        BigDecimal result = BigDecimal.ZERO;
        for(Enumeration<String> paramNames = req.getParameterNames(); paramNames.hasMoreElements();) {
            String parameter = req.getParameter(paramNames.nextElement()).trim();

            if(parameter.isEmpty()){
                RequestDispatcher dispatcher = req.getRequestDispatcher("/missing_parameter_error.html");
                dispatcher.forward(req, resp);
                return;
            }
            Matcher numMatcher = NUMBER_PATTERN.matcher(parameter);
            if(!numMatcher.matches()) {
                RequestDispatcher dispatcher = req.getRequestDispatcher("/nan_error.html");
                dispatcher.forward(req, resp);
                return;
            }
            result = result.add(new BigDecimal(parameter));
        }
        writer.write("<h3>RESULT = ");
        writer.write(result.toString());
        writer.write("</h3><h4><a href=\"http://localhost:8080/\">Return</a></h4>");
        writer.close();
    }

}
