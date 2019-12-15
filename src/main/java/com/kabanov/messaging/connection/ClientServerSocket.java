package com.kabanov.messaging.connection;

import java.net.Socket;

/**
 * Pair of client and server socket
 */
public class ClientServerSocket {
    
    private final Socket clientSocket;
    private final Socket serverSocket;

    public ClientServerSocket(Socket clientSocket, Socket serverSocket) {
        this.clientSocket = clientSocket;
        this.serverSocket = serverSocket;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public Socket getServerSocket() {
        return serverSocket;
    }
}
