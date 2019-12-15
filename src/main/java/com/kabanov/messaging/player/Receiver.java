package com.kabanov.messaging.player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.kabanov.messaging.player.messages.Message;
import com.kabanov.messaging.transport.ParcelTransport;
import com.kabanov.messaging.transport.data.Parcel;

/**
 * @author Kabanov Alexey
 */
public class Receiver {

    private ParcelTransport parcelTransport;
    private String componentName;

    public Receiver(@Nonnull String componentName, @Nonnull ParcelTransport parcelTransport) {
        this.parcelTransport = parcelTransport;
        this.componentName = componentName;
    }

    @Nullable
    public Message receive() {
        Parcel pack = parcelTransport.receive(componentName);
        return pack != null ? pack.getBody() : null;
    }
}
