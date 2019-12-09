package com.kabanov.messaging.messages;

/**
 * @author Kabanov Alexey
 */
public class Message {
    
    private String text;

    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
