package org.project.Launch;
import org.apache.commons.cli.*;

import org.project.ClientPack.Client;
import org.project.Controller.Control.Controller;
import org.project.Controller.Server.Settings;

import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Launcher class that starts the application, either as a client or as a server.
 * The launcher uses command line arguments to determine the IP address, Socket Port,
 * and RMI Port for the server.
 */

public class Launcher {
    private static String inText;
    private static Scanner scanner = new Scanner(System.in);

    /**
     * The main method that starts the application.
     * It parses command line arguments for configuration settings, prompts the user to
     * select the mode (client or server), and then launches the application in the
     * selected mode.
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException {

        Options options = new Options();

        Option param1 = new Option("IP", "ServerIP", true, "Server Ip to connect at");
        param1.setRequired(false);
        options.addOption(param1);
        Option param2 = new Option("SP", "Socket Port", true, "Socket Port to connect at");
        param2.setRequired(false);
        options.addOption(param2);
        Option param3 = new Option("RMIP", "RMI Port", true, "RMI Port to connect at");
        param3.setRequired(false);
        options.addOption(param3);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
            return;
        }

        Settings.SERVER_NAME = cmd.getOptionValue("IP", "127.0.0.1");
        Settings.SOCKET_PORT = Integer.parseInt(cmd.getOptionValue("SP", "5678"));
        Settings.RMI_PORT = Integer.parseInt(cmd.getOptionValue("RMIP", "5679"));

        System.out.println("Start as [C]lient or [S]erver? (Default: [C]): ");
        inText = scanner.nextLine().toUpperCase();

        if (inText.equals("S")) {
            Controller controller = new Controller();
            controller.launch(args);
        } else if (inText.equals("C")) {
            Client client = new Client();
            client.launch();
        }
        // Default: Client
        else {
            System.out.println("Starting as Client..");
            Client client = new Client();
            client.launch();
        }
    }
}
