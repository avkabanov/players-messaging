package com.kabanov.messaging.player.messages;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kabanov Alexey
 */
public class IncrementingReplyCreatorTest {

    private FakeCounter sentMessageCounter = new FakeCounter();
    private IncrementingReplyCreator replyCreator = new IncrementingReplyCreator(sentMessageCounter);
    
    @Test
    public void shouldIncreaseTheNumberInReply() {
        sentMessageCounter.setCounter(0);
        
        String firstMessage = "hello";
        String firstExpectedReply = "hello0";
        String firstReply = replyCreator.createReply(firstMessage);
        Assert.assertEquals(firstExpectedReply, firstReply);

        sentMessageCounter.setCounter(1);
        String secondExpectedReply = "hello01"; 
        String secondReply = replyCreator.createReply(firstReply);
        Assert.assertEquals(secondExpectedReply, secondReply);
    }
    
    private static class FakeCounter implements SentMessageCounter {
        private int counter;

        public void setCounter(int counter) {
            this.counter = counter;
        }

        @Override
        public int getNumberOfSentMessages() {
            return counter;
        }
    }

}