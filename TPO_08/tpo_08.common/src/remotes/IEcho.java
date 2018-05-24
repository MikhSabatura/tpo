package remotes;

import communciates.EchoRequest;
import communciates.EchoResponse;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IEcho extends Remote{
    EchoResponse echo(EchoRequest request) throws RemoteException;
}
