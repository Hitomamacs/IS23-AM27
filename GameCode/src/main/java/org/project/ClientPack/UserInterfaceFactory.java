package org.project.ClientPack;

import org.project.ClientPack.ClientView;
import org.project.ClientPack.ConnectionInterface;
import org.project.ClientPack.UserInterface;

/**
 * An interface for creating user interfaces for clients.
 */

public interface UserInterfaceFactory {

    /**
     * Creates a user interface for a client.
     * @param clientView The client view object associated with the user interface.
     * @param ConnectionInterface The connection interface object used for client communication.
     * @return An instance of the UserInterface representing the user interface for the client.
     */
    UserInterface createUserInterface(ClientView clientView, ConnectionInterface ConnectionInterface);
}
