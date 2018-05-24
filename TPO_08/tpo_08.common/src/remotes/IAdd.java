package remotes;

import communciates.AddRequest;
import communciates.AddResponse;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAdd extends Remote {
    AddResponse add(AddRequest request) throws RemoteException;
}
