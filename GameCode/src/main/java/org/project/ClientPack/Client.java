package org.project.ClientPack;

import org.project.Controller.Server.Settings;
import org.project.Gui.GuiUserInterfaceFactory;

import java.util.Locale;
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
    public  void launch(String args[]){

        String connessione;
        String userInterface;
        final Scanner stdin= new Scanner(System.in);
        ClientFactory clientFactory=null;
        UserInterfaceFactory userInterfaceFactory=null;

        //Controllo se le porte mi vengono passate in ingresso
        //TODO: anche server name può essere passato?
        if(args.length!=0){
            socketPort=Integer.parseInt(args[0]);
            rmiPort=Integer.parseInt((args[0]));
        }

        System.out.println("Che tipo di connessione vuoi usare (rmi/socket)?");
        connessione= stdin.nextLine();
        connessione = connessione.toLowerCase();

        while(!connessione.equals("rmi") && !connessione.equals("socket")){
            System.out.println("Inserisci un tipo di connessione valido (rmi/socket)");
            connessione = stdin.nextLine();
            connessione =connessione.toLowerCase();
        }

        switch(connessione){
            case "rmi":
                try{
                     clientFactory= new RMIClientFactory();
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case "socket":
                 try{
                      clientFactory= new SocketClientFactory();
                 }catch(Exception e ){
                    e.printStackTrace();
                 }
                break;
        }

        System.out.println("Che tipo di UserInterface vuoi usare (CLI/GUI)?");
        userInterface= stdin.nextLine();
        userInterface = userInterface.toUpperCase();

        while(!userInterface.equals("CLI") && !userInterface.equals("GUI")){
            System.out.println("Inserisci un tipo di UserInterface valido (CLI/GUI)");
            userInterface = stdin.nextLine();
            userInterface = userInterface.toUpperCase();
        }

        switch (userInterface){
            case "CLI":
                try{
                     userInterfaceFactory= new CliUserInterfaceFactory();
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case "GUI":
                try{
                     userInterfaceFactory= new GuiUserInterfaceFactory();
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
        }
        client = new GeneralClient();
        try{
            client.startClient(clientFactory, userInterfaceFactory);

    }catch (Exception e){
            e.printStackTrace();
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

