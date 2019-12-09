package com.kabanov.messaging.transport;

import javax.annotation.Nonnull;

import com.kabanov.messaging.messages.Message;

/**
 * @author Kabanov Alexey
 */
public class Package {
    private String receiverName;
    private Message message;

    public Package(String receiverName, Message message) {
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
