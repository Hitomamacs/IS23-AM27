package org.project;

import java.rmi.RemoteException;
import java.util.Scanner;

public class ProvaClient {

    public static void main(String args[]){
        RMIClientApp client;
        final Scanner stdin= new Scanner(System.in);

        try{
            client=new RMIClientApp(1234);
        }catch(RemoteException e){
            throw new RuntimeException(e);
        }

        client.startClient();

    }
}
