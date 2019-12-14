package com.kabanov.messaging.parcel;

import javax.annotation.Nullable;

import com.kabanov.messaging.transport.Parcel;

/**
 * @author Kabanov Alexey
 */
public interface ParcelTransport {

    void send(Parcel message);

    @Nullable
    Parcel receive(String recipient);
    
}
