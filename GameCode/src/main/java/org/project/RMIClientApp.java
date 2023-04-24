package org.project;

import org.project.Controller.Server.RMIServerInterface;
import org.project.Controller.Server.Settings;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClientApp extends UnicastRemoteObject implements RMIClientInterface {

    /**
     * reference to the server object
     */
    private RMIServerInterface rmiServer;
    private int port;
    String nickname;

    /**
     * constructor
     * @throws RemoteException
     */
    public RMIClientApp(int port) throws RemoteException{
        this.port=port;
    }

    /**
     * method that opens a connection with the RMI server
     * @throws Exception
     */
    public void startClient() throws Exception{
        String nick;
        //Getting the registry
        Registry registry;
        registry= LocateRegistry.getRegistry(Settings.SERVER_NAME,port);

        //Looking up the registry for the remote object
        this.rmiServer= (RMIServerInterface) registry.lookup("Server");
        System.out.println("Connessione stabilita");
        /*final Scanner stdin= new Scanner(System.in);
        System.out.println("Quale nickname vuoi utilizzare?");
        nick=stdin.nextLine();
        this.rmiServer.sendLogin(nick, false,this);*/
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
