package com.kabanov.messaging.player.messages;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kabanov Alexey
 */
public class IncrementingReplyCreatorTest {

    private IncrementingReplyCreator replyCreator = new IncrementingReplyCreator();

    @Test
    public void shouldIncreaseTheNumberInReply() {

        final int initialReplyNumber = 0;

        String firstMessage = "hello";
        String firstReply = replyCreator.createReply(firstMessage);
        int replyDigit = getLastDigitFromReply(firstReply, firstMessage);
        Assert.assertEquals(initialReplyNumber, replyDigit);     // first reply digit should be always zero

        int expectedSecondReplyDigit = initialReplyNumber + 1;
        String secondMessage = "world";
        String secondReply = replyCreator.createReply(secondMessage);
        Assert.assertEquals(expectedSecondReplyDigit, getLastDigitFromReply(secondReply, secondMessage));
    }

    private int getLastDigitFromReply(String reply, String firstMessage) {
        reply = reply.replace(firstMessage, "");
        return Integer.parseInt(reply);
    }

}