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

    // TODO: 02.06.2018 0. review and refactor

    private Connection connection;
    private Session session;

    private static int id_count = 0;
    private static final int THREAD_COUNT = 5;
    private static final ExecutorService executorService;
    private static final String NAME_PREFIX = "SERVICE-";

    static {
        executorService = Executors.newFixedThreadPool(THREAD_COUNT);
    }

    public Service() {
        super();
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
        ObjectMessage requestMessage = (ObjectMessage) message;
        try {
            IRequest requestObj = (IRequest) requestMessage.getObject();
            logger.info("request received " + requestObj);
            Destination responseQueue = (Destination) getContext().lookup(requestObj.getSenderName());

            IResponse responseObj = RequestProcessor.processRequest(requestObj);
            logger.info("starting to process the request");
            Thread.sleep(3000 + new Random().nextInt(2000));
            logger.info("request processed");
            ObjectMessage responseMessage = session.createObjectMessage(responseObj);

            session.createProducer(responseQueue).send(responseMessage);
            logger.info("response sent " + responseObj);
        } catch (Exception e) {
            throw new Assignment_09_exception(e);
        }
    }

    @Override
    protected String getNamePrefix() {
        return NAME_PREFIX;
    }

    @Override
    protected int getId() {
        return ++id_count;
    }
}
