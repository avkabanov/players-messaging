package com.kabanov.messaging.parcel;

import java.io.Serializable;

import javax.annotation.Nonnull;

import com.kabanov.messaging.messages.Message;

/**
 * @author Kabanov Alexey
 */
public class Parcel implements Serializable {
    private String receiverName;
    private Message message;

    public Parcel(String receiverName, Message message) {
        this.receiverName = receiverName;
        this.message = message;
    }

    public String getReceiverName() {
        return receiverName;
    }

    @Nonnull
    public Message getMessage() {
        return message;
    }
}
