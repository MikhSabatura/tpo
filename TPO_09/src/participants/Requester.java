package participants;

import exceptions.Assignment_09_exception;
import requests.ArithmeticRequest;
import requests.ArithmeticRequestType;
import requests.IRequest;
import requests.RandomRequest;
import responses.ArithmeticResponse;
import responses.IResponse;
import responses.RandomResponse;

import javax.jms.*;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
            MessageProducer requestMessageProducer = session.createProducer(getRequestQueue());

            Destination resultQueue = (Destination) getContext().lookup(name);
            MessageConsumer responseMessageConsumer = session.createConsumer(resultQueue);

            connection.start();
            for (int i = 0; i < 3; i++) {
                IRequest requestObj = produceRequest();
                sendRequest(session, requestObj, requestMessageProducer);
                IResponse responseObj = receiveResponse(responseMessageConsumer);
                processResponse(responseObj);
            }
            //not sure if these lines are needed at all:
        } catch (JMSException | NamingException e) {
            e.printStackTrace();
            throw new Assignment_09_exception(e);
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    throw new Assignment_09_exception(e);
                }
            }
        }
    }

    private void processResponse(IResponse responseObj) {
        // TODO: 03.06.2018 log the whole thing
        if(responseObj instanceof RandomResponse) {
            RandomResponse randomResponse = (RandomResponse) responseObj;
            System.out.println(name + randomResponse.getResult() + " random");
        } else if (responseObj instanceof ArithmeticResponse) {
            ArithmeticResponse arithmeticResponse = (ArithmeticResponse) responseObj;
            System.out.println(name + " " + arithmeticResponse.getResult() + " arithmetic");
        } else {
            throw new Assignment_09_exception("ILLEGAL RESPONSE TYPE");
        }
    }

    private IRequest produceRequest() {
        Random random = new Random();
        if (random.nextInt(2) == 0) {
            return new RandomRequest(name);
        } else {
            BigDecimal p1 = new BigDecimal(random.nextInt(1000));
            BigDecimal p2 = new BigDecimal(random.nextInt(1000));
            ArithmeticRequestType reqType = ArithmeticRequestType.values()[random.nextInt(ArithmeticRequestType.values().length)];
            return new ArithmeticRequest(p1, p2, reqType, name);
        }
    }

    private void sendRequest(Session session, IRequest requestObj, MessageProducer messageProducer) throws JMSException {
        Message requestMessage = session.createObjectMessage(requestObj);
        messageProducer.send(requestMessage);
    }

    private IResponse receiveResponse(MessageConsumer messageConsumer) throws JMSException {
        return (IResponse) ((ObjectMessage) messageConsumer.receive()).getObject();
    }

}
