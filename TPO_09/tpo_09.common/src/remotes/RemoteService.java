package remotes;

import communciates.AddRequest;
import communciates.AddResponse;
import communciates.EchoRequest;
import communciates.EchoResponse;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteService extends UnicastRemoteObject implements IEcho, IAdd {

    public RemoteService() throws RemoteException {
    }

    @Override
    public EchoResponse echo(EchoRequest request) throws RemoteException {
        return new EchoResponse(request.getRequest());
    }

    @Override
    public AddResponse add(AddRequest request) throws RemoteException {
        return new AddResponse(request.getParam1().add(request.getParam2()));
    }
}
