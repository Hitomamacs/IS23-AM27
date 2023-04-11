package org.example;

import java.rmi.RemoteException;
import java.util.HashMap;

public class Server {
    /**
     * LOCK to avoid the concurrency between players during login phase
     */
    private static final Object Login_lock= new Object();

    /**
     * HashMap that contains all players
     * string is player's username
     */
    private HashMap<String, Player> players;

    /**
     * RMI server
     */
    private static RMIServerApp rmiServer;

    /**
     * SOCKET server
     */
    //aggiungo un socket

    /**
     * constructor
     */
    public Server() throws RemoteException {
        players= new HashMap<>();
        //socketServer= new SocketServer()
        rmiServer= new RMIServerApp();
    }

    /**
     * MAIN
     */
    public static void main (String[] args) throws RemoteException {
        int socketPort; //lo metto uguale al settings.socketport
        int rmiPort=Settings.RMI_PORT;

        if(args.length!=0){
            socketPort=Integer.parseInt(args[0]);
            rmiPort=Integer.parseInt((args[1]));
        }

        try{
            Server server=new Server();
            rmiServer.startRMIServer(rmiPort);
            //socketServer.startRMIServer(socketPort);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    //METODI CHE IL CLIENT PUO CHIAMARE SUL SERVER
    public void login(String nickname, RMIClientApp client){
        //vedo metodo scritto da ale
    }

    public void quit (String nickname, RMIClientApp client){
        //vedo metodo scritto da ale
    }

   public void pick (String nickname,RMIClientApp client){
        //metodo ale
   }
    public void topUp (String nickname,RMIClientApp client){
        //metodo ale
    }

    public void sendMessage (String message){
        //chiamo metodo sul client che mi stampa il messaggio
    }
    

}
