package com.kabanov.messaging.player.behavior;

import javax.annotation.Nonnull;

import com.kabanov.messaging.player.Receiver;
import com.kabanov.messaging.player.Sender;
import com.kabanov.messaging.player.messages.Message;
import com.kabanov.messaging.player.messages.MessageCreator;
import com.kabanov.messaging.player.messages.ReplyCreator;
import com.kabanov.messaging.properties.InitiatorPlayerProperties;

/**
 * @author Kabanov Alexey
 */
public class InitiativeBehavior implements Behavior {

    private Sender sender;
    private Receiver receiver;
    private MessageCreator messageCreator;
    private ReplyCreator replyCreator;
    private String opponentName;
    private InitiatorPlayerProperties initiatorPlayerProperties;
    private volatile boolean stopFlag = false;

    public InitiativeBehavior(@Nonnull Sender sender, @Nonnull Receiver receiver,
                              @Nonnull MessageCreator messageCreator, @Nonnull ReplyCreator replyCreator,
                              @Nonnull String opponentName,
                              @Nonnull InitiatorPlayerProperties initiatorPlayerProperties) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageCreator = messageCreator;
        this.replyCreator = replyCreator;
        this.opponentName = opponentName;
        this.initiatorPlayerProperties = initiatorPlayerProperties;
    }

    @Override
    public void invoke() {
        String text = messageCreator.createMessage();
        Message initialMessage = new Message(text);
        sender.send(opponentName, initialMessage);

        for (int i = 0; i < initiatorPlayerProperties.getNumberOfMessagesToSend() && !stopFlag; i++) {
            Message message = receiver.receive();
            if (message != null) {
                String reply = replyCreator.createReply(message.getText());
                Message replyMessage = new Message(reply);
                sender.send(opponentName, replyMessage);
            } else {
                stop();
            }
        }
    }

    @Override
    public void stop() {
        stopFlag = true;
    }
}
