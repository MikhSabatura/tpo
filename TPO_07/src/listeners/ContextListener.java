package listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
        logger.log(Level.INFO, "Handling context initialization event " + new Date());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
        logger.log(Level.INFO, "Handling context destruction event " + new Date());
    }
}
