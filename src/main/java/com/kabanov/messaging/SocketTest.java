package com.kabanov.messaging;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author Kabanov Alexey
 */
public class SocketTest {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        ForkJoinTask<Socket> future = new ForkJoinPool().submit(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(4444);
                return serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });
        
        Socket clientSocket = new Socket("localhost", 4444);
        Socket serverSocket = future.get();

        ForkJoinTask<?> future2 = ForkJoinPool.commonPool().submit(() -> createStreams(clientSocket));
        ForkJoinTask<?> future3 = ForkJoinPool.commonPool().submit(() -> createStreams(serverSocket));
        
        future2.get();
        future3.get();

        System.out.println("success");
        
    }

    private static void createStreams(Socket socket) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

        } catch (IOException ex) {
            System.out.println("Exception");
        }
        System.out.println("created");
    }

}
