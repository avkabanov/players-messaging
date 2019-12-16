package com.kabanov.messaging.player.messages;

/**
 * @author Kabanov Alexey
 */
public class IncrementingReplyCreator implements ReplyCreator {

    private int respondsCounter;

    @Override
    public String createReply(String message) {
        return message + createSuffix();
    }

    public String createSuffix() {
        return String.valueOf(respondsCounter++);
    }
}
