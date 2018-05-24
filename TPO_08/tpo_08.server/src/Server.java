import remotes.RemoteService;
import rmi_info.Info;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    private static Registry rmiRegistry;

    public static void main(String[] args) {
        try {
            rmiRegistry = LocateRegistry.createRegistry(Info.RMI_PORT);
            RemoteService service = new RemoteService();
            rmiRegistry.rebind(Info.SERVICE_OBJECT_NAME, service);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("server started");
    }
}
