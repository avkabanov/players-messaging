package com.kabanov.messaging.player.behavior;

import javax.annotation.Nonnull;

import com.kabanov.messaging.player.Receiver;
import com.kabanov.messaging.player.Sender;
import com.kabanov.messaging.player.messages.Message;
import com.kabanov.messaging.player.messages.MessageCreator;
import com.kabanov.messaging.properties.InitiatorPlayerProperties;

/**
 * @author Kabanov Alexey
 */
public class InitiativeBehavior implements Behavior {

    private Sender sender;
    private Receiver receiver;
    private MessageCreator messageCreator;
    private String opponentName;
    private InitiatorPlayerProperties initiatorPlayerProperties;
    private volatile boolean stopFlag = false;

    public InitiativeBehavior(@Nonnull Sender sender, @Nonnull Receiver receiver,
                              @Nonnull MessageCreator messageCreator, @Nonnull String opponentName,
                              @Nonnull InitiatorPlayerProperties initiatorPlayerProperties) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageCreator = messageCreator;
        this.opponentName = opponentName;
        this.initiatorPlayerProperties = initiatorPlayerProperties;
    }

    @Override
    public void invoke() {
        for (int i = 0; i < initiatorPlayerProperties.getNumberOfMessagesToSend() && !stopFlag; i++) {
            String text = messageCreator.createMessage();
            Message message = new Message(text);
            sender.send(opponentName, message);
            receiver.receive();
        }
    }

    @Override
    public void stop() {
        stopFlag = true;
    }
}
