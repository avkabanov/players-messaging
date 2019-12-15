package com.kabanov.messaging.player.messages;

import java.io.Serializable;

/**
 * @author Kabanov Alexey
 */
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
