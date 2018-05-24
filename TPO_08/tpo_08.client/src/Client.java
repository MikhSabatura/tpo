import communciates.AddRequest;
import communciates.AddResponse;
import communciates.EchoRequest;
import communciates.EchoResponse;
import remotes.IAdd;
import remotes.IEcho;
import rmi_info.Info;

import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

public class Client {

    private static Registry rmiRegistry;


    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        Random random = new Random();

        try {
            rmiRegistry = LocateRegistry.getRegistry(Info.RMI_PORT);

            AddRequest addRequest = new AddRequest(new BigDecimal(random.nextInt(1000)), new BigDecimal(random.nextInt(1000)));
            System.out.println("Add request: " + addRequest);
            IAdd addRemoteObj = (IAdd) rmiRegistry.lookup(Info.SERVICE_OBJECT_NAME);
            AddResponse addResponse = addRemoteObj.add(addRequest);
            System.out.println("Add response: " + addResponse);

            EchoRequest echoRequest = new EchoRequest("message");
            System.out.println("Echo request: " + echoRequest);
            IEcho echoRemoteObj = (IEcho) rmiRegistry.lookup(Info.SERVICE_OBJECT_NAME);
            EchoResponse echoResponse = echoRemoteObj.echo(echoRequest);
            System.out.println("Echo response: " + echoResponse);
        } catch (IOException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
