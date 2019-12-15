package com.kabanov.messaging.player;

import javax.annotation.Nonnull;

import com.kabanov.messaging.player.messages.Message;
import com.kabanov.messaging.transport.ParcelTransport;
import com.kabanov.messaging.transport.data.Parcel;

/**
 * @author Kabanov Alexey
 */
public class Sender {

    private ParcelTransport parcelTransport;

    public Sender(@Nonnull ParcelTransport parcelTransport) {
        this.parcelTransport = parcelTransport;
    }

    public void send(@Nonnull String opponentName, @Nonnull Message message) {
        parcelTransport.send(new Parcel(opponentName, message));
    }
}
