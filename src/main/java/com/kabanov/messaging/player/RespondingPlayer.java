package com.kabanov.messaging.player;

import com.kabanov.messaging.Event;
import com.kabanov.messaging.messages.Message;
import com.kabanov.messaging.messages.ReplyCreator;
import com.kabanov.messaging.transport.Package;
import com.kabanov.messaging.transport.PackageTransport;

/**
 * @author Kabanov Alexey
 */
public class RespondingPlayer implements Player {
    private ReplyCreator replyCreator;
    private PackageTransport packageTransport;
    private boolean stopFlag = false;
    private String opponentName;

    public RespondingPlayer(ReplyCreator replyCreator,
                            PackageTransport packageTransport) {
        this.replyCreator = replyCreator;
        this.packageTransport = packageTransport;
    }

    @Override
    public void setOpponentName(String opponentName) {

        this.opponentName = opponentName;
    }

    @Override
    public String getName() {
        return "Responding_player";
    }

    @Override
    public void run() {
        while (!stopFlag) {
            Message message = receiveMessage();
            Message reply = replyCreator.createReply(message);
            send(reply);
        }
    }

    @Override
    public void stop() {
        stopFlag = true;
    }

    @Override
    public void send(Message message) {
        packageTransport.send(new Package(opponentName, message));
    }

    @Override
    public Message receiveMessage() {
        return packageTransport.receive(getName()).getMessage();
    }

    @Override
    public void onEventReceived(Event event) {
        switch (event) {
            case STOP:
                stop();
                break;
        }
    }
}
