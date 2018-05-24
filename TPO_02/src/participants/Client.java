package participants;

import exceptions.Assignment02Exception;
import messages.MessageType;
import messages.Request;
import messages.Response;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;

public class Client extends Transmitter {

    private final static int port = 50012;
    private final String host = "localhost";

    private SocketChannel channel;

    public Client(InetAddress serverAddress, int serverPort) throws IOException {
        try {
            this.channel = SocketChannel.open();
            this.channel.bind(new InetSocketAddress(host, port));
            this.channel.configureBlocking(false);
            this.channel.connect(new InetSocketAddress(serverAddress, serverPort));
            ensureConnection();
        } catch (IOException e) {
            this.channel.close();
            throw new Assignment02Exception(e);
        }
        System.out.println("Connected to the server " + this.channel.getRemoteAddress());
    }

    public static void main(String[] args) {
        try {
            Client client = new Client(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
            String reqStr = extractRequest(args);
            client.communicate(reqStr);
        } catch (UnknownHostException | NumberFormatException e) {
            throw new Assignment02Exception(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String extractRequest(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 2; i < args.length - 1; i++) {
            stringBuilder.append(args[i] + " ");
        }
        stringBuilder.append(args[args.length - 1]);
        return stringBuilder.toString();
    }

    //perform communication with the server

    public void communicate(String str) {
        Request request = Request.parseRequest(str);
        if (request.getType() == MessageType.ILLEGAL) {
            System.out.println("ILLEGAL REQUEST");
            return;
        }
        sendRequest(request);
        System.out.println("SENT REQUEST: " + str);
        Response response = getResponse();
        System.out.println("RESPONSE: " + response);
    }

    public void sendRequest(Request request) throws Assignment02Exception {
        sendMessage(channel, request.toString());
    }

    public Response getResponse() {
        return Response.parseResponse(receiveMessage(channel));
    }

    public void ensureConnection() {
        try {
            while (!channel.finishConnect()) {
                System.out.println("sleep" + channel.isConnected());
                Thread.sleep(100);
            }
        } catch (Exception e) {
            throw new Assignment02Exception("COULDN'T ESTABLISH CONNECTION", e);
        }
    }

}
