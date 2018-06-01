package participants;

import exceptions.Assignment_09_exception;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public abstract class Participant {

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
