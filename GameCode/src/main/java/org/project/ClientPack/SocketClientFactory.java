package org.project.ClientPack;

import org.project.ClientPack.ClientFactory;
import org.project.ClientPack.ConnectionInterface;
import org.project.ClientPack.SocketClient;

public class SocketClientFactory implements ClientFactory {

    @Override
    public ConnectionInterface createClient() {
        return new SocketClient();
    }

}
