package com.kabanov.messaging.di.factory;

import com.kabanov.messaging.parcel.InMemoryParcelTransport;
import com.kabanov.messaging.parcel.ParcelTransport;

/**
 * @author Kabanov Alexey
 */
public class ThreadObjectsFactory extends BaseObjectsFactory implements ObjectsFactory {

    private ParcelTransport parcelTransport = new InMemoryParcelTransport();

    @Override
    public ParcelTransport getParcelTransport() {
        return parcelTransport;
    }
}
