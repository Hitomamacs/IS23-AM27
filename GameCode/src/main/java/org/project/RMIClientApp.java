package org.project;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RMIClientApp extends UnicastRemoteObject implements RMIClientInterface {

    /**
     * reference to the server object
     */
    private RMIServerInterface rmiServer;
    String nickname;

    /**
     * constructor
     * @throws RemoteException
     */
    public RMIClientApp() throws RemoteException{}

    /**
     * method that opens a connection with the RMI server
     * @throws Exception
     */
    public void startClientRMI() throws Exception{
        //Getting the registry
        Registry registry;
        registry= LocateRegistry.getRegistry(Settings.SERVER_NAME,Settings.RMI_PORT);

        //Looking up the registry for the remote object
        this.rmiServer= (RMIServerInterface) registry.lookup("Server");
        final Scanner stdin= new Scanner(System.in);
        nickname=stdin.nextLine();
        this.rmiServer.sendLogin(nickname, false,this);
    }

    /**
     * method that shows the player a new message
     * @param nickname nickname of the author of the message
     * @param message message text
     * @throws RemoteException
     */
    public void printMsg (String nickname, String message) throws RemoteException{
        System.out.println(message);
    }

    public String getNickname() {
        return nickname;
    }
}
