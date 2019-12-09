package com.kabanov.messaging.event;

/**
 * @author Kabanov Alexey
 */
public interface EventListener {

    void onEventReceived(Event event);
}
