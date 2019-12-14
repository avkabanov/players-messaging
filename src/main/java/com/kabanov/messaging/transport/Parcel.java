package com.kabanov.messaging.transport;

import javax.annotation.Nonnull;

import com.kabanov.messaging.messages.Message;

/**
 * @author Kabanov Alexey
 */
public class Parcel implements Transferable<Message> {
    private String receiverName;
    private Message message;

    public Parcel(String receiverName, Message message) {
        this.receiverName = receiverName;
        this.message = message;
    }

    @Override
    public String getReceiverName() {
        return receiverName;
    }

    @Override
    @Nonnull
    public Message getBody() {
        return message;
    }
}
