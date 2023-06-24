package org.project.ClientPack;

import org.project.ClientPack.ClientView;
import org.project.ClientPack.ConnectionInterface;
import org.project.ClientPack.UserInterface;

public interface UserInterfaceFactory {
    UserInterface createUserInterface(ClientView clientView, ConnectionInterface ConnectionInterface);
}
