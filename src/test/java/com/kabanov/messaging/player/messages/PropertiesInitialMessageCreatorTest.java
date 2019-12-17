package com.kabanov.messaging.player.messages;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.kabanov.messaging.properties.InitiatorPlayerProperties;

/**
 * @author Kabanov Alexey
 */
@RunWith(MockitoJUnitRunner.class)
public class PropertiesInitialMessageCreatorTest {

    @Mock private InitiatorPlayerProperties initiatorPlayerProperties;
    private PropertiesInitialMessageCreator messageCreator;

    @Before
    public void setup() {
        messageCreator = new PropertiesInitialMessageCreator(initiatorPlayerProperties);
    }

    @Test
    public void shouldCreateMessageFromProperties() {
        String expectedMessage = "initial message";
        Mockito.when(initiatorPlayerProperties.getInitialMessage()).thenReturn(expectedMessage);

        String actualMessage = messageCreator.createMessage();

        Assert.assertEquals(expectedMessage, actualMessage);
    }

}