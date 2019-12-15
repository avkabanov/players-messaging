package com.kabanov.messaging.transport;

import javax.annotation.Nullable;

import com.kabanov.messaging.transport.data.Parcel;

/**
 * @author Kabanov Alexey
 */
public interface ParcelTransport {

    void send(Parcel message);

    @Nullable
    Parcel receive(String recipient);
    
}
