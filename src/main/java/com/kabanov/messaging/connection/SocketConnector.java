package com.kabanov.messaging.connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author Kabanov Alexey
 */
public class SocketConnector {

    public static ClientServerSocket openClientAndServerSocket(InetSocketAddress remoteAddress, int localPort) 
            throws ExecutionException, InterruptedException {
        
        ForkJoinTask<Socket> localServerSocket = ForkJoinPool.commonPool().submit(() -> openServerSocket(localPort));

        // player2 socket 
        ForkJoinTask<Socket> remoteClientSocket = ForkJoinPool.commonPool()
                .submit(() -> SocketConnector.openClientSocket(remoteAddress.getHostName(),
                        remoteAddress.getPort()));

        return new ClientServerSocket(localServerSocket.get(), remoteClientSocket.get());
    }

    public static Socket openServerSocket(int port) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverSocket.accept();
    }

    public static Socket openClientSocket(String host, int port) {
        do {
            try {
                return new Socket(host, port);
            } catch (IOException e) {
                System.out.println("Error creating socket: " + e.getMessage());
            }
        } while (true);
    }
}
