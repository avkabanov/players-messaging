package com.kabanov.messaging.event;

/**
 * @author Kabanov Alexey
 */
public interface EventTransport {
    
    void register(String name, EventListener eventListener);

    void sendEvent(String recipient, Event event);

}
