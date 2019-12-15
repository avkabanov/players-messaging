package com.kabanov.messaging.transport;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

/**
 * @author Kabanov Alexey
 */
public class SocketTransport<D> implements Transport<Parcel<D>, D> {

    private ConcurrentHashMap<String, SocketWrapper> listeners = new ConcurrentHashMap<>();

    public void register(String name, Socket socket) throws IOException {
        if (listeners.putIfAbsent(name, new SocketWrapper(socket)) == null) {
            System.out.println("Registered player " + name + " with socket: " + socket);
        }
    }

    @Override
    public void send(Parcel<D> message) {
        try {
            listeners.get(message.getReceiverName()).getOutputStream().writeObject(message);
            listeners.get(message.getReceiverName()).getOutputStream().flush();

            System.out.println("Message " + message.getBody() + " sent to " + message.getReceiverName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public Parcel<D> receive(String recipient) {
        try {
            Parcel<D> parcel = (Parcel) listeners.get(recipient).getInputStream().readObject();
            System.out.println("Message " + parcel.getBody() + " received by " + recipient);
            return parcel;
        } catch (IOException e) {
            // ignore. Just return null in case of io exception
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    static class SocketWrapper {
        private ObjectOutputStream outputStream;
        private volatile ObjectInputStream inputStream;
        private Socket socket;

        public SocketWrapper(Socket socket) throws IOException {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();
            this.socket = socket;
        }

        /**
         * if we run sender and receiver in a single JVM - we may get blocked on ObjectInputStream constructor. That is
         * why we can not instantiate in SocketWrapper constructor, but need to make lazy initialization using double
         * checked locking.
         */
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
