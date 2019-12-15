package com.kabanov.messaging.event;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;

import com.kabanov.messaging.transport.Parcel;
import com.kabanov.messaging.transport.SocketTransport;
import com.kabanov.messaging.transport.Transport;

/**
 * @author Kabanov Alexey
 */
public class SocketEventTransport implements EventTransport {

    private SocketTransport<Event> parcelTransport = new SocketTransport<>();
    private ConcurrentHashMap<String, EventListener> eventListeners = new ConcurrentHashMap<>();

    public void register(String name, EventListener eventListener, Socket socket) throws IOException {
        parcelTransport.register(name, socket);
        register(name, eventListener);
    }
    
    @Override
    public void register(String name, EventListener eventListener) {
        EventListener currentValue = eventListeners.putIfAbsent(name, eventListener);
        if (currentValue != null) {
            throw new IllegalArgumentException("Already registered");
        }
        ForkJoinPool.commonPool().submit(() -> {
            new Receiver(name, parcelTransport);
        });
    }

    @Override
    public void sendEvent(String recipient, Event event) {
        EventListener result = eventListeners.get(recipient);

        if (result == null) {
            throw new IllegalArgumentException("No listener " + recipient + " registered");
        } else {
            result.onEventReceived(event);
        }
    }

    class Receiver implements Runnable {

        private String recipient;
        private Transport<Parcel<Event>, Event> transport;

        Receiver(String recipient, Transport<Parcel<Event>, Event> transport) {
            this.recipient = recipient;
            this.transport = transport;
        }

        @Override
        public void run() {
            while (true) {
                Parcel<Event> message = transport.receive(recipient);
                if (message != null) {
                    sendEvent(recipient, message.getBody());
                }
            }
        }
    }
}
