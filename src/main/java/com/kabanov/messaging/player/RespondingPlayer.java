package com.kabanov.messaging.player;

import com.kabanov.messaging.event.Event;
import com.kabanov.messaging.messages.Message;
import com.kabanov.messaging.messages.ReplyCreator;
import com.kabanov.messaging.transport.Package;
import com.kabanov.messaging.transport.PackageTransport;

/**
 * @author Kabanov Alexey
 */
public class RespondingPlayer implements EventListeningPlayer {
    private ReplyCreator replyCreator;
    private PackageTransport packageTransport;
    private boolean stopFlag = false;
    private String opponentName;
    private Thread playersThread;

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
        playersThread = Thread.currentThread();
        while (!stopFlag) {
            Message message = receiveMessage();
            if (message != null) {
                Message reply = replyCreator.createReply(message);
                send(reply);
            }
        }
        System.out.println("Player " + getName() + " stopped");
    }

    @Override
    public void stop() {
        // need to add synchromization on 
        stopFlag = true;
        playersThread.interrupt();
    }

    @Override
    public void send(Message message) {
        packageTransport.send(new Package(opponentName, message));
    }

    @Override
    public Message receiveMessage() {
        Package pack = packageTransport.receive(getName());
        return pack != null? pack.getMessage() : null;
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
