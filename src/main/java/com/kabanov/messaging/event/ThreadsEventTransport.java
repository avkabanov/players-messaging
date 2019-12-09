package com.kabanov.messaging.event;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Kabanov Alexey
 */
public class ThreadsEventTransport implements EventTransport {

    private ConcurrentHashMap<String, EventListener> events = new ConcurrentHashMap<>();

    @Override
    public void register(String name, EventListener eventListener) {
        EventListener currentValue = events.putIfAbsent(name, eventListener);
        if (currentValue != null) {
            throw new IllegalArgumentException("Already registered");
        }
    }

    @Override
    public void sendEvent(String recipient, Event event) {
        EventListener result = events.get(recipient);

        if (result == null) {
            throw new IllegalArgumentException("No listener " + recipient + " registered");
        } else {
            result.onEventReceived(event);
        }
    }
}
