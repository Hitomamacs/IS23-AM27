package org.project.Gui;

import org.project.ClientPack.ClientView;
import org.project.ClientPack.ConnectionInterface;
import org.project.ClientPack.UserInterface;
import org.project.ClientPack.UserInterfaceFactory;

/**
 * The factory helps to provide a standardized way to create GuiUserInterface instances,
 * allowing separation of the responsibilities of creating user interfaces from the components that use them
 */
public class GuiUserInterfaceFactory implements UserInterfaceFactory {

    /**
     * Constructor
     * @param clientView
     * @param ConnectionInterface
     * @return GuiUserInterface object
     */
    @Override
    public UserInterface createUserInterface(ClientView clientView, ConnectionInterface ConnectionInterface) {
        return new GuiUserInterface(clientView, ConnectionInterface);
    }
}
