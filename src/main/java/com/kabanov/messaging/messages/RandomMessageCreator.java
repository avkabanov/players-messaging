package com.kabanov.messaging.messages;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author Kabanov Alexey
 */
public class RandomMessageCreator implements MessageCreator {
    
    @Override
    public Message createMessage() {
        return new Message(RandomStringUtils.randomAlphabetic(10));
    }
}
