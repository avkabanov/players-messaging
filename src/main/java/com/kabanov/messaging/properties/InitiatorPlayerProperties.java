package com.kabanov.messaging.properties;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Kabanov Alexey
 */
public class InitiatorPlayerProperties {

    private final String initialMessage;
    private int numberOfMessagesToSend;
    
    public InitiatorPlayerProperties(String propertiesName) throws IOException {
        Properties properties = PropertiesLoader.loadFromResource(propertiesName);
        numberOfMessagesToSend = Integer.parseInt(properties.getProperty("number_of_messages_to_send"));
        initialMessage = properties.getProperty("initial_message");
    }

    public static InitiatorPlayerProperties loadFrom(String propertiesName) throws IOException {
        return new InitiatorPlayerProperties(propertiesName);
    }

    public int getNumberOfMessagesToSend() {
        return numberOfMessagesToSend;
    }

    public String getInitialMessage() {
        return initialMessage;
    }
}


