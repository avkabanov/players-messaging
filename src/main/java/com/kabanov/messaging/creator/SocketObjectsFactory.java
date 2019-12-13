package com.kabanov.messaging.creator;

import com.kabanov.messaging.event.EventTransport;
import com.kabanov.messaging.event.ThreadsEventTransport;
import com.kabanov.messaging.parcel.ParcelTransport;
import com.kabanov.messaging.parcel.SocketParcelTransport;

/**
 * @author Kabanov Alexey
 */
public class SocketObjectsFactory extends BaseObjectsFactory implements ObjectsFactory {

    private ParcelTransport socketParcelTransport = new SocketParcelTransport();

    @Override
    public ParcelTransport getParcelTransport() {
        return socketParcelTransport;
    }

    @Override
    public EventTransport createEventTransport() {
        return new ThreadsEventTransport();
    }
}
