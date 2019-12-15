package com.kabanov.messaging.transport;

import javax.annotation.Nullable;

/**
 * @author Kabanov Alexey
 */

// TODO google if there is really a necessity to specify second "D"
public interface Transport<T extends Transferable<D>, D> {

    void send(T parcel);

    @Nullable
    T receive(String recipient);
    
}
