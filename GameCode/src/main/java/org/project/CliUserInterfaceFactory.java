package org.project;

public class CliUserInterfaceFactory implements UserInterfaceFactory {

    @Override
    public UserInterface createUserInterface() {
        return new CliUserInterface();
    }

}
