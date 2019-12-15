package com.kabanov.messaging.di.factory;

import com.kabanov.messaging.event.EventTransport;
import com.kabanov.messaging.event.SocketEventTransport;
import com.kabanov.messaging.transport.SocketTransport;
import com.kabanov.messaging.transport.Transport;

/**
 * @author Kabanov Alexey
 */
public class SocketObjectsFactory extends BaseObjectsFactory implements ObjectsFactory {

    private Transport socketTransport = new SocketTransport();

    private EventTransport eventTransport = new SocketEventTransport();
    
    @Override
    public Transport getTransport() {
        return socketTransport;
    }

    @Override
    public EventTransport getEventTransport() {
        return eventTransport;
    }
}
