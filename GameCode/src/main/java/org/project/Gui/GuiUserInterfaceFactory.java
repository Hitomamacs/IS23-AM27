package org.project.Gui;

import org.project.ClientPack.ClientView;
import org.project.ClientPack.ConnectionInterface;
import org.project.ClientPack.UserInterface;
import org.project.ClientPack.UserInterfaceFactory;

public class GuiUserInterfaceFactory implements UserInterfaceFactory {
    @Override
    public UserInterface createUserInterface(ClientView clientView, ConnectionInterface ConnectionInterface) {
        return new GuiUserInterface(clientView, ConnectionInterface);
    }

}
