package com.kabanov.messaging.player.messages;

/**
 * @author Kabanov Alexey
 */
public class IncrementingReplyCreator implements ReplyCreator {

    private SentMessageCounter sentMessageCounter;

    public IncrementingReplyCreator(SentMessageCounter sentMessageCounter) {
        this.sentMessageCounter = sentMessageCounter;
    }

    @Override
    public String createReply(String message) {
        return message + createSuffix();
    }

    public String createSuffix() {
        return String.valueOf(sentMessageCounter.getNumberOfSentMessages());
    }
}
