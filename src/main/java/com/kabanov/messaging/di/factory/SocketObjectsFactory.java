package com.kabanov.messaging.di.factory;

import com.kabanov.messaging.transport.ParcelTransport;
import com.kabanov.messaging.transport.SocketParcelTransport;

/**
 * @author Kabanov Alexey
 */
public class SocketObjectsFactory extends BaseObjectsFactory implements ObjectsFactory {

    private ParcelTransport socketParcelTransport = new SocketParcelTransport();

    @Override
    public ParcelTransport getParcelTransport() {
        return socketParcelTransport;
    }
}
