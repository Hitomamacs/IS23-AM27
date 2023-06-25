package org.project.Launch;

import org.project.ClientPack.Client;
import org.project.Controller.Control.Controller;

import java.util.Scanner;

public class Launcher {
    private static String inText;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Start as [C]lient or [S]erver? (Default: [C]): ");
        inText = scanner.nextLine().toUpperCase();

        if (inText.equals("S")) {
            Controller.main(args);
        } else if (inText.equals("C")) {
            Client.main(args);
        }
        // Default: Client
        else {
            System.out.println("Starting as Client..");
            Client.main(args);
        }
    }
}
