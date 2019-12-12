package com.kabanov.messaging.messages;

import java.io.Serializable;

/**
 * @author Kabanov Alexey
 */
// TODO rename parcel to message and message to messageBOdy
public class Message implements Serializable {
    
    private String text;

    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                '}';
    }
}
