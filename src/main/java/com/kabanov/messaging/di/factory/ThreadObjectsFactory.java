package com.kabanov.messaging.di.factory;

import com.kabanov.messaging.event.EventTransport;
import com.kabanov.messaging.transport.InMemoryTransport;
import com.kabanov.messaging.transport.Transport;

/**
 * @author Kabanov Alexey
 */
public class ThreadObjectsFactory extends BaseObjectsFactory implements ObjectsFactory {

    private Transport transport = new InMemoryTransport();

    @Override
    public Transport getTransport() {
        return transport;
    }

    @Override
    public EventTransport getEventTransport() {
        return null;
    }
}
