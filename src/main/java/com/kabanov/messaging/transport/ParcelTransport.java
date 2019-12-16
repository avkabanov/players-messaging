package com.kabanov.messaging.transport;

import javax.annotation.Nullable;

import com.kabanov.messaging.transport.data.Parcel;

/**
 * @author Kabanov Alexey
 */
public interface ParcelTransport {

    void send(Parcel message);

    /**
     * Receive is a blocking method. It returns a parcel, when Parcel is received or null if connection is broken, and
     * no more messages will be received
     */
    @Nullable
    Parcel receive(String recipient);

}
