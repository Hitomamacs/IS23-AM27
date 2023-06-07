package org.project.Gui;

import org.project.ClientView;
import org.project.UserInterface;
import org.project.UserInterfaceFactory;

public class GuiUserInterfaceFactory implements UserInterfaceFactory {
    @Override
    public UserInterface createUserInterface(ClientView clientView) {
        return new GuiUserInterface(clientView);
    }

}
