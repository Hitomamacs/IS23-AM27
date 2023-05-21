package org.project;

public class CliUserInterfaceFactory implements UserInterfaceFactory {

    @Override
    public UserInterface createUserInterface(ClientView clientView) {
        return new CliUserInterface(clientView);
    }

}
