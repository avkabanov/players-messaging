package com.kabanov.messaging.player.messages;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author Kabanov Alexey
 */
public class RandomMessageCreator implements MessageCreator {
    
    @Override
    public String createMessage() {
        return RandomStringUtils.randomAlphabetic(10);
    }
}
