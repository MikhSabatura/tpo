package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

@WebFilter(urlPatterns = "*.html", dispatcherTypes = DispatcherType.REQUEST)
public class HeaderFilter implements Filter {

    private static final String CHARSET = "UTF-8";
    private static final String TEXT_CONTENT_TYPE = "text/html";

    private static final String OPEN_TAGS_PATH = "/html/openTags.html";
    private static final String HEADER_PATH = "/html/header.html";

    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        resp.setContentType(TEXT_CONTENT_TYPE);
        resp.setCharacterEncoding(CHARSET);
        System.out.println("header");

        FileWriter.printFile(resp.getWriter(), context, OPEN_TAGS_PATH);
        FileWriter.printFile(resp.getWriter(), context, HEADER_PATH);
        filterChain.doFilter(req, resp);

        System.out.println("/header");
    }

}