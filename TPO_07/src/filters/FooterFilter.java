package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(urlPatterns = "*.html", dispatcherTypes = DispatcherType.REQUEST)
public class FooterFilter implements Filter {

    private static final String CLOSING_TAGS_PATH = "/html/closingTags.html";
    private static final String FOOTER_PATH = "/html/footer.html";

    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("footer");
        CustomResponseWrapper wrapper = new CustomResponseWrapper((HttpServletResponse) resp);
        filterChain.doFilter(req, wrapper);

        PrintWriter out = resp.getWriter();
        System.out.println("before");
        String content = wrapper.getStringWriter().toString();
        System.out.println("after");
        out.write(content);

        FileWriter.printFile(out, context, CLOSING_TAGS_PATH);
        FileWriter.printFile(out, context, FOOTER_PATH);
        System.out.println("/footer");
    }
}
