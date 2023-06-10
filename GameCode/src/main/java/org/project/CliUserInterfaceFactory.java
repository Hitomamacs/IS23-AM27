package org.project;

public class CliUserInterfaceFactory implements UserInterfaceFactory {

    @Override
    public UserInterface createUserInterface(ClientView clientView, ConnectionInterface ConnectionInterface) {
        return new CliUserInterface(clientView, ConnectionInterface);
    }


}
