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
        //TODO: anche server name può essere passato?
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


    // METODI  CHE CHIAMANO IL SERVER
    //TODO: avrò i metodi di login, send chat message e metodi per fare le mosse, questi metodi chiameranno i metodi presenti in RMI/SOCKET client che chismeranno i metodi del server


    //GETTER

    /**
     * metodo getter che ritorna la socket port scelta
     */
    public static int getSocketPort() {
        return socketPort;
    }

    /**
     *metodo che ritorna il client
     */
    public static ClientInterface getClient() {
        return client;
    }

    /**
     * metodo che ritorna la porta rmi scelta
     */
    public static int getRmiPort() {
        return rmiPort;
    }

    /**
     *metodo che ritorna il nickname finale
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * metodo che ritorna la client view
     */
    public ClientView getClientView() {
        return clientView;
    }

    /**
     *metodo che ritorna il flag che mi dice se una partita è già iniziata
     */
    public boolean isGameStarted() {
        return gameStarted;
    }



}

