package org.project;

import org.project.Controller.Server.RMIServerInterface;
import org.project.Controller.Server.Settings;
import org.project.Model.Coordinates;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class RMIClient extends UnicastRemoteObject implements ConnectionInterface, RMIClientInterface{

    /**
     * reference to the server object
     */

    private int num_tiles;
    private RMIServerInterface rmiServer;
    /**
     * port for the communication
     */
    private int port;
    /**
     * nickname chosen by the player
     */
    String nickname;

    final Scanner stdin= new Scanner(System.in);
    /**
     * reference to the client view
     */
    private ClientView clientView= new ClientView();

    public RMIClient() throws RemoteException {
        this.port= Settings.RMI_PORT;
        startClient();
    }

    public void startClient () {
        boolean nome;
        boolean successo;
        final Scanner stdin= new Scanner(System.in);
        int port=Settings.RMI_PORT;
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(Settings.SERVER_NAME,port);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        //Looking up the registry for the remote object
        try {
            rmiServer= (RMIServerInterface) registry.lookup("Server");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Connessione stabilita con successo");
    }

    @Override
    public void SendJoinMessage() {
        System.out.println("Inserisci nome");
        nickname=stdin.nextLine();
        try {
            rmiServer.sendJoin(nickname,false, this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void SendCreateGameMessage(){
        System.out.println("Inserisci nome");
        nickname=stdin.nextLine();
        try {
            rmiServer.sendCreateGame(nickname,false,2, this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void SendQuitMessage() {

    }

    @Override
    public void SendPickMessage() {

        int x,y;
        List<Coordinates> coordinates= new ArrayList<>();
        System.out.println("Enter x coordinate: ");
        x=stdin.nextInt();
        System.out.println("Enter y coordinate: ");
        y=stdin.nextInt();
        coordinates.add(new Coordinates(x,y));
        try {
            rmiServer.sendPickRequest(nickname, coordinates);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void SendTopUpMessage() {
        int column, tileIndex;
        System.out.println("Inserisci colonna :  ");
        column=stdin.nextInt();
        System.out.println("Inserisci tile index : ");
        tileIndex=stdin.nextInt();
        try {
            rmiServer.sendTopUpRequest(nickname,column,tileIndex);
        } catch (RemoteException e) {
            throw new RuntimeException(e);

    }


}
}
