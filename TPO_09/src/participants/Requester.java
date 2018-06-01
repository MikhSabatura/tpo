package participants;

import exceptions.Assignment_09_exception;

import javax.jms.*;
import javax.naming.NamingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;

public class Requester extends Participant implements Runnable {

    private int id;
    private final String name;

    private static int ID;
    private static final int THREAD_COUNT = 10;
    private static final ExecutorService executorService;
    private static final String NAME_PREFIX = "REQUESTER-";

    static {
        ID = 0;
        executorService = Executors.newFixedThreadPool(THREAD_COUNT);
    }

    public Requester() {
        this.id = ++ID;
        this.name = NAME_PREFIX + id;
    }

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(new Requester());
        }
        System.out.println("Requester threads started");
    }

    @Override
    public void run() {
        Connection connection = null;
        try {
            connection = getConnectionFactory().createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination resultQueue = session.createQueue(name);
            getContext().rebind(name, resultQueue);

        } catch (JMSException | NamingException e) {
            throw new Assignment_09_exception(e);
        }
    }
}
