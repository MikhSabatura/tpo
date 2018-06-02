package participants;

import exceptions.Assignment_09_exception;
import logic.RequestProcessor;
import requests.IRequest;
import responses.IResponse;

import javax.jms.*;
import javax.naming.NamingException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service extends Participant implements Runnable, MessageListener {


    // TODO: 02.06.2018 0 add all packages as trusted

    // TODO: 02.06.2018 1. make the bitch work
    // TODO: 02.06.2018 2. logging actions
    // TODO: 02.06.2018 3. review and refactor

    private int id;
    private final String name;

    private Connection connection;
    private Session session;

    private static int ID;
    private static final int THREAD_COUNT = 5;
    private static final ExecutorService executorService;
    private static final String NAME_PREFIX = "SERVICE-";

    static {
        ID = 0;
        executorService = Executors.newFixedThreadPool(THREAD_COUNT);
    }

    public Service() {
        this.id = ++ID;
        this.name = NAME_PREFIX + id;
        try {
            this.connection = getConnectionFactory().createConnection();
            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            throw new Assignment_09_exception(e);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(new Service());
        }
        System.out.println("Service threads started");
    }

    @Override
    public void run() {
        try {
            connection.start();
            MessageConsumer requestConsumer = session.createConsumer(getRequestQueue());
            requestConsumer.setMessageListener(this);
        } catch (JMSException e) {
            throw new Assignment_09_exception(e);
        }
    }

    @Override
    public void onMessage(Message message) {
        System.out.println("message received");
        ObjectMessage requestMessage = (ObjectMessage) message;
        try {
            IRequest requestObj = (IRequest) requestMessage.getObject();
            //getting the queue from jndi using the producer's name
            Destination responseQueue = (Destination) getContext().lookup(requestObj.getSenderName());

            IResponse responseObj = RequestProcessor.processRequest(requestObj);
            Thread.sleep(3000 + new Random().nextInt(2000));
            ObjectMessage responseMessage = session.createObjectMessage(responseObj);

            session.createProducer(responseQueue).send(responseMessage);
        } catch (Exception e) {
            throw new Assignment_09_exception(e);
        }
    }
}
