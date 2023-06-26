package org.project.Launch;

import org.project.ClientPack.Client;
import org.project.Controller.Control.Controller;

import java.rmi.RemoteException;
import java.util.Scanner;

public class Launcher {
    private static String inText;
    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) throws RemoteException {

        SecondLauncher secondLauncher= new SecondLauncher();
        secondLauncher.launch(args);

    }
}
