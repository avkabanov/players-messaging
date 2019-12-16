package com.kabanov.messaging.player.behavior;

import javax.annotation.Nonnull;

import com.kabanov.messaging.player.Receiver;
import com.kabanov.messaging.player.Sender;
import com.kabanov.messaging.player.messages.Message;
import com.kabanov.messaging.player.messages.ReplyCreator;

/**
 * @author Kabanov Alexey
 */
public class RecipientBehavior implements Behavior {

    private Sender sender;
    private Receiver receiver;
    private ReplyCreator replyCreator;
    private String opponentName;
    private volatile boolean stopFlag;

    public RecipientBehavior(@Nonnull Sender sender, @Nonnull Receiver receiver,
                             @Nonnull ReplyCreator replyCreator, @Nonnull String opponentName) {
        this.sender = sender;
        this.receiver = receiver;
        this.replyCreator = replyCreator;
        this.opponentName = opponentName;
    }

    @Override
    public void invoke() {
        while (!stopFlag) {
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
