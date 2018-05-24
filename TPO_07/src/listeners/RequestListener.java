package listeners;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class RequestListener implements ServletRequestListener {
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        StringBuilder requestContent = new StringBuilder();
        for (Map.Entry<String, String[]> entry : sre.getServletRequest().getParameterMap().entrySet()) {
            requestContent.append(entry.getKey()).append("=").append(Arrays.toString(entry.getValue()));
        }
        Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
        logger.log(Level.INFO, "Request initialized: " + requestContent);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
        logger.log(Level.INFO, "Request destroyed");
    }
}
