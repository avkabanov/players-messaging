package com.kabanov.messaging.parcel;

import javax.annotation.Nullable;

/**
 * @author Kabanov Alexey
 */
public interface ParcelTransport {
    
    void send(Parcel message);

    @Nullable
    Parcel receive(String recipient);
    
}
