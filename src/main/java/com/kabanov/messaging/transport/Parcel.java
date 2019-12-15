package com.kabanov.messaging.transport;

import javax.annotation.Nonnull;

/**
 * @author Kabanov Alexey
 */
public class Parcel<T> implements Transferable<T> {
    private String receiverName;
    private T body;

    public Parcel(String receiverName, T body) {
        this.receiverName = receiverName;
        this.body = body;
    }

    @Override
    public String getReceiverName() {
        return receiverName;
    }

    @Override
    @Nonnull
    public T getBody() {
        return body;
    }
}
