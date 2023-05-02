package org.project;

/**
 * interfaccia usata per poter utilizzare qualsiasi tipo di connessione (SOCKET o RMI)
 */
public interface ClientInterface extends Runnable {

    /**
     * metodo che apre una connessione con il server
     * @throws Exception
     */
    public void startClient() throws Exception;

    /**
     *prova a fare una login al server con il nome scelto
     * @param nickname nome scelto per fare la login
     */
    public void sendLoginRequest(String nickname);

    /**
     *Invia un messaggio nella chat comune
     * @param message messaggio da inviare
     */
    public void sendMessage(String message);

    /**
     *invia al server la richiesta di prendere delle tessere nella board
     */
    public void sendPickRequest();

    /**
     *invia al server la colonna scelta per posizionare le tessere scelte
     */
    public void sendTopUpRequest();
}
