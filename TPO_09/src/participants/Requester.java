package participants;

import exceptions.Assignment_09_exception;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;

public class Requester extends Participant implements Runnable {

    private int id;

    private static int ID;
    private static final int THREAD_COUNT = 10;
    private static final ExecutorService executorService;

    static {
        ID = 0;
        executorService = Executors.newFixedThreadPool(THREAD_COUNT);
    }

    public Requester() {
        this.id = ++ID;
    }

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(new Requester());
        }
    }

    @Override
    public void run() {
        Connection connection = null;
        try {
            connection = getConnectionFactory().createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            throw new Assignment_09_exception(e);
        }
    }
}
