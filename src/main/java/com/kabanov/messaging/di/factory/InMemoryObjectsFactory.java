package com.kabanov.messaging.di.factory;

import com.kabanov.messaging.transport.InMemoryParcelTransport;
import com.kabanov.messaging.transport.ParcelTransport;

/**
 * @author Kabanov Alexey
 */
public class InMemoryObjectsFactory extends BaseObjectsFactory implements ObjectsFactory {

    private ParcelTransport parcelTransport = new InMemoryParcelTransport();

    @Override
    public ParcelTransport getParcelTransport() {
        return parcelTransport;
    }
}
