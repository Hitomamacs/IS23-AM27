package org.project.Launch;

import org.project.ClientPack.Client;
import org.project.Controller.Control.Controller;

import java.rmi.RemoteException;

public class SecondLauncher {
    //java jar nome jar
    //server : --server ip port
    //client : --client ip port
    //dafault parte il client
    public void launch(String args[]) throws RemoteException {
        if(args[1].equals("--server")){
            Controller controller = new Controller();
            controller.launch(args);
        }
        else{
            Client client = new Client();
            client.launch(args);
        }
    }
}
