package com.kabanov.messaging.player.behavior;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.kabanov.messaging.player.Receiver;
import com.kabanov.messaging.player.Sender;
import com.kabanov.messaging.player.messages.Message;
import com.kabanov.messaging.player.messages.MessageCreator;
import com.kabanov.messaging.player.messages.ReplyCreator;
import com.kabanov.messaging.properties.InitiatorPlayerProperties;

/**
 * @author Kabanov Alexey
 */
@RunWith(MockitoJUnitRunner.class)
public class InitiativeBehaviorTest {
    private final static String opponentName = "Player2";
    private final static int numberOfMessagesInConversation = 10;
    
    @Mock private Sender sender;
    @Mock private Receiver receiver;
    @Mock private MessageCreator messageCreator;
    @Mock private ReplyCreator replyCreator;
    @Mock private InitiatorPlayerProperties playerProperties;
    private InitiativeBehavior initiativeBehavior;

    @Before
    public void setup() {
        initiativeBehavior = new InitiativeBehavior(sender, receiver, messageCreator, replyCreator, opponentName,
                playerProperties);
        Mockito.when(playerProperties.getNumberOfMessagesToSend()).thenReturn(numberOfMessagesInConversation);
        Mockito.when(receiver.receive()).thenReturn(new Message("stub message"));
    }

    /**
     * verify that `create message`, `send` and `receive` are invoked specific number of times
     */
    @Test
    public void shouldRepeatTheCycleSpecificNumberOfTimes() {
        initiativeBehavior.invoke();
        Mockito.verify(messageCreator, Mockito.times(1)).createMessage();  // initial message
        
        // + 1 for initial message
        Mockito.verify(sender, Mockito.times(numberOfMessagesInConversation + 1)).send(Mockito.eq(opponentName), Mockito.any());
        Mockito.verify(receiver, Mockito.times(numberOfMessagesInConversation)).receive();
    }

}