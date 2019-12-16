package com.kabanov.messaging.player.behavior;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.kabanov.messaging.player.Receiver;
import com.kabanov.messaging.player.Sender;
import com.kabanov.messaging.player.messages.MessageCreator;
import com.kabanov.messaging.properties.InitiatorPlayerProperties;

/**
 * @author Kabanov Alexey
 */
@RunWith(MockitoJUnitRunner.class)
public class InitiativeBehaviorTest {
    private final static String opponentName = "Player2";
    private final static int numberOfMessagesToSend = 10;

    @Mock private Sender sender;
    @Mock private Receiver receiver;
    @Mock private MessageCreator messageCreator;
    @Mock private InitiatorPlayerProperties playerProperties;
    private InitiativeBehavior initiativeBehavior;

    @Before
    public void setup() {
        initiativeBehavior = new InitiativeBehavior(sender, receiver, messageCreator, opponentName, playerProperties);
        Mockito.when(playerProperties.getNumberOfMessagesToSend()).thenReturn(numberOfMessagesToSend);
    }

    /**
     * verify that `create message`, `send` and `receive` are invoked specific number of times
     */
    @Test
    public void shouldRepeatTheCycleSpecificNumberOfTimes() {
        initiativeBehavior.invoke();
        Mockito.verify(messageCreator, Mockito.times(numberOfMessagesToSend)).createMessage();
        Mockito.verify(sender, Mockito.times(numberOfMessagesToSend)).send(Mockito.eq(opponentName), Mockito.any());
        Mockito.verify(receiver, Mockito.times(numberOfMessagesToSend)).receive();
    }

}