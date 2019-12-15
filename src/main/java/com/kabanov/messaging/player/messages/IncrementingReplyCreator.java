package com.kabanov.messaging.player.messages;

/**
 * @author Kabanov Alexey
 */
public class IncrementingReplyCreator implements ReplyCreator {

    private int respondsCounter;

    @Override
    public Message createReply(Message message) {
        return new Message(generateReply(message.getText()));
    }

    public String generateReply(String text) {
        return text +"_" + respondsCounter++;
    }
}
