package org.project;

import org.project.Controller.Server.Settings;

import java.util.Scanner;

/**
 * client of the game
 */

public class Client {

    /**
     * porta socket
     */
    static int socketPort= Settings.SOCKET_PORT;
    /**
     * porta RMI
     */
    static int  rmiPort= Settings.RMI_PORT;

    /**
     * client scelto (RMI O SOCKET)
     */
    static private ClientInterface client;
    /**
     * nickname scelto
     */
    private String nickname;
    /**
     * riferimento alla view del client
     */
    private ClientView clientView;
    /**
     * booleano che mi dice se la partita è iniziata
     */
    private boolean gameStarted;


    /**
     * constructor
     */
    public Client() {
        nickname="client";
        clientView= new ClientView();
        gameStarted=false;
    }

    /**
     * MAIN
     */
    public static void main(String args[]){

        String connessione;
        final Scanner stdin= new Scanner(System.in);

        //Controllo se le porte mi vengono passate in ingresso
        if(args.length!=0){
            socketPort=Integer.parseInt(args[0]);
            rmiPort=Integer.parseInt((args[0]));
        }

        System.out.println("Che tipo di connessione vuoi usare (rmi/socket)?");
        connessione= stdin.nextLine();
        switch(connessione){
            case "rmi":
                try{
                    client= new RMIClientApp(rmiPort);
                    client.startClient();
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case "socket":
                try{
                    client= new SocketClientApp();
                    client.startClient();
                }catch(Exception e ){
                    e.printStackTrace();
                }
                break;
        }

    }

    //GETTER
    public static int getSocketPort() {
        return socketPort;
    }

    public static ClientInterface getClient() {
        return client;
    }

    public static int getRmiPort() {
        return rmiPort;
    }

    public String getNickname() {
        return nickname;
    }

    public ClientView getClientView() {
        return clientView;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

}