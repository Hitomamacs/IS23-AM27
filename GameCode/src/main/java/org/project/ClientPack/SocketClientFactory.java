package org.project.ClientPack;

import org.project.ClientPack.ClientFactory;
import org.project.ClientPack.ConnectionInterface;
import org.project.ClientPack.SocketClient;

/**
 * A factory class for creating SocketClient objects that implement the ConnectionInterface.
 */

public class SocketClientFactory implements ClientFactory {

    /**
     * Creates a new SocketClient object that implements the ConnectionInterface.
     * @return A new instance of the SocketClient class representing the client connection.
     */
    @Override
    public ConnectionInterface createClient() {
        return new SocketClient();
    }
}
