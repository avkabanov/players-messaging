package com.kabanov.messaging.creator;

import com.kabanov.messaging.event.EventTransport;
import com.kabanov.messaging.parcel.ParcelTransport;
import com.kabanov.messaging.parcel.ThreadsParcelTransport;

/**
 * @author Kabanov Alexey
 */
public class ThreadObjectsFactory extends BaseObjectsFactory implements ObjectsFactory {

    private ParcelTransport parcelTransport = new ThreadsParcelTransport();

    @Override
    public ParcelTransport getParcelTransport() {
        return parcelTransport;
    }

    @Override
    public EventTransport createEventTransport() {
        return null;
    }
}
