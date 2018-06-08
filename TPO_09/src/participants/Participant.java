package participants;

import exceptions.Assignment_09_exception;
import org.apache.log4j.Logger;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Participant implements Runnable {

    protected final int id;
    protected final String name;
    protected final Logger logger;

    private static Context context;
    private static ConnectionFactory connectionFactory;
    private static Destination requestQueue;

    private static final String CONNECTION_FACTORY_NAME = "connectionFactory";
    private static final String REQUEST_QUEUE_NAME = "RequestQueue";

    static {
        try {
            context = new InitialContext();
            connectionFactory = (ConnectionFactory) context.lookup(CONNECTION_FACTORY_NAME);
            requestQueue = (Destination) context.lookup(REQUEST_QUEUE_NAME);
        } catch (NamingException e) {
            throw new Assignment_09_exception(e);
        }
    }

    public Participant() {
        this.id = computeId();
        this.name = getNamePrefix() + id;
        this.logger = Logger.getLogger(name);
    }

    public static void main(String[] args) {
        final int serviceThreadCount = Integer.parseInt(args[0]);
        final int requesterThreadCount = Integer.parseInt(args[1]);
        ExecutorService executorService = Executors.newFixedThreadPool(serviceThreadCount + requesterThreadCount);
        for (int i = 0; i < serviceThreadCount; i++) {
            executorService.submit(new Service());
        }
        for (int i = 0; i < requesterThreadCount; i++) {
            executorService.submit(new Requester());
        }
        executorService.shutdown();
    }

    protected abstract String getNamePrefix();

    protected abstract int computeId();

    public static Context getContext() {
        return context;
    }

    public static ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public static Destination getRequestQueue() {
        return requestQueue;
    }

}
