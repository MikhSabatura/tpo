package participants;

import exceptions.Assignment02Exception;
import messages.Request;
import messages.RequestProcessor;
import messages.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server extends Transmitter {

    private final static int port = 50001;
    private final String host = "localhost";

    private ServerSocketChannel serverChannel;
    private Selector selector;

    public Server() throws IOException {
        try {
            this.serverChannel = ServerSocketChannel.open();
            this.serverChannel.bind(new InetSocketAddress(host, port));
            this.serverChannel.configureBlocking(false);

            this.selector = Selector.open();
            this.serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            this.serverChannel.close();
            if (this.selector != null) {
                selector.close();
            }
            throw new Assignment02Exception(e);
        }
        System.out.println("Server socket created, address " + this.serverChannel.getLocalAddress());
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.processConnections();
        } catch (Assignment02Exception | IOException e) {
            e.printStackTrace();
        }
    }

    public void processConnections() throws Assignment02Exception {
        while (true) {
            try {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (key.isAcceptable()) {
                        SocketChannel sc = serverChannel.accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                        System.out.println("Client " + sc.getRemoteAddress() + " connected");
                    } else if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        processClientRequest(sc);
                        //unregister and close the channel:
                        key.cancel();
                        sc.close();
                    }
                }
            } catch (IOException e) {
                throw new Assignment02Exception(e);
            }
        }
    }

    private void processClientRequest(SocketChannel channel) {
        String reqStr = receiveMessage(channel);
        if (reqStr == null) {
            System.out.println("Client disconnected");
            return;
        }
        Request request = Request.parseRequest(reqStr);//receiving the request
        System.out.println("RECEIVED: " + reqStr);
        Response response = RequestProcessor.processRequest(request);
        sendResponse(channel, response);
    }

    private void sendResponse(SocketChannel channel, Response response) {
        sendMessage(channel, response.toString());
        System.out.println("SENT RESPONSE: " + response);
    }

}
