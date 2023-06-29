package org.project.ClientPack;

/**
 * Interface used to be able to use any type of connection (SOCKET or RMI)
 */
public interface ClientInterface {

    /**
     * The method starts a connection with the server
     * @throws Exception
     */
    public void startClient(ClientFactory clientFactory, UserInterfaceFactory userInterfaceFactory) throws Exception;
}
