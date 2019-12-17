package com.kabanov.messaging.player.messages;

import com.kabanov.messaging.properties.InitiatorPlayerProperties;

/**
 * @author Kabanov Alexey
 */
public class PropertiesInitialMessageCreator implements MessageCreator {
    
    private InitiatorPlayerProperties initiatorPlayerProperties;

    public PropertiesInitialMessageCreator(InitiatorPlayerProperties initiatorPlayerProperties) {
        this.initiatorPlayerProperties = initiatorPlayerProperties;
    }

    @Override
    public String createMessage() {
        return initiatorPlayerProperties.getInitialMessage();
    }
}
