package org.project.ClientPack;

public class CliUserInterfaceFactory implements UserInterfaceFactory {

    /**
     * Creates a user interface for a client in a command-line interface (CLI) environment.
     * @param clientView The client view object associated with the user interface.
     * @param ConnectionInterface The connection interface object used for client communication.
     * @return A new instance of the CliUserInterface class representing the CLI user interface.
     */
    @Override
    public UserInterface createUserInterface(ClientView clientView, ConnectionInterface ConnectionInterface) {
        return new CliUserInterface(clientView, ConnectionInterface);
    }
}
