package com.kabanov.messaging.parcel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

/**
 * @author Kabanov Alexey
 */
public class SocketParcelTransport implements ParcelTransport {

    // player 1 
    // player 2

    private ConcurrentHashMap<String, SocketWraper> listeners = new ConcurrentHashMap<>();

    public void register(String name, Socket socket) throws IOException {
        if (listeners.putIfAbsent(name, new SocketWraper(socket)) == null) {
            System.out.println("Add " + socket + " for player " + name);    
        } else {
            System.out.println("Ignored " + socket + " for player " + name);
        }
    }

    @Override
    public void send(Parcel message) {
        try {
            listeners.get(message.getReceiverName()).getOutputStream().writeObject(message);
            listeners.get(message.getReceiverName()).getOutputStream().flush();

            System.out.println("Message " + message.getMessage() + " sent to " + message.getReceiverName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public Parcel receive(String recipient) {
        try {
            Parcel parcel = (Parcel) listeners.get(recipient).getInputStream().readObject();
            System.out.println("Message " + parcel.getMessage() + " received by " + recipient);;
            return parcel;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    class SocketWraper {
        private ObjectOutputStream outputStream;
        private volatile ObjectInputStream inputStream;
        private Socket socket;

        public SocketWraper(Socket socket) throws IOException {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();
            
            this.socket = socket;
        }

        public Socket getSocket() {
            return socket;
        }

        public void setSocket(Socket socket) {
            this.socket = socket;
        }

        /**
         * if we run sender and receiver in a single JVM - we may get blocked on ObjectInputStream constructor. 
         * That is why we can not instantiate in SocketWrapper constructor, but need to make lazy initialization 
         * using double checked locking.
         * @return
         * @throws IOException
         */
        // todo maybe remove that??
        public ObjectInputStream getInputStream() throws IOException {
            if (inputStream == null) {
                synchronized (this) {
                    if (inputStream == null) {
                        inputStream = new ObjectInputStream(socket.getInputStream());            
                    }
                }
            }
            return inputStream;
        }

        public ObjectOutputStream getOutputStream() {
            return outputStream;
        }
    }
}
