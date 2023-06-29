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
     * socket port
     */
    static int socketPort= Settings.SOCKET_PORT;
    /**
     *  RMI port
     */
    static int  rmiPort= Settings.RMI_PORT;

    /**
     * client connection(RMI O SOCKET)
     */
    static private ClientInterface client;
    /**
     * nickname of the player
     */
    private String nickname;
    /**
     * reference client view
     */
    private ClientView clientView;
    /**
     * boolean. it is true if game is started
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
     * Launches the client application.
     * Client choose if he wants rmi/socket connection and use CLI/GUI
     *
     * @param args Command-line arguments
     */
    public  void launch(){

        String connessione;
        String userInterface;
        final Scanner stdin= new Scanner(System.in);
        ClientFactory clientFactory=null;
        UserInterfaceFactory userInterfaceFactory=null;

        //Controllo se le porte mi vengono passate in ingresso
        //TODO: anche server name può essere passato?


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

