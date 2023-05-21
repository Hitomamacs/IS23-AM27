package org.project;

public class SocketClientFactory implements ClientFactory {

    @Override
    public ConnectionInterface createClient() {
        return new SocketClient();
    }

}
