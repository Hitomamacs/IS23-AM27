package org.project;

public interface UserInterfaceFactory {
    UserInterface createUserInterface(ClientView clientView, ConnectionInterface ConnectionInterface);
}
