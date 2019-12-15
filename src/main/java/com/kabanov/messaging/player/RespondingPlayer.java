package com.kabanov.messaging.player;

import com.kabanov.messaging.event.Event;
import com.kabanov.messaging.messages.Message;
import com.kabanov.messaging.messages.ReplyCreator;
import com.kabanov.messaging.transport.Parcel;
import com.kabanov.messaging.transport.Transport;

/**
 * @author Kabanov Alexey
 */
public class RespondingPlayer implements EventListeningPlayer {
    private String playerName;
    private ReplyCreator replyCreator;
    private Transport<Parcel<Message>, Message> transport;
    private volatile boolean stopFlag = false;
    private String opponentName;
    private Thread playersThread;

    public RespondingPlayer(String playerName, String opponentName, ReplyCreator replyCreator,
                            Transport<Parcel<Message>, Message> transport) {
        this.playerName = playerName;
        this.opponentName = opponentName;
        this.replyCreator = replyCreator;
        this.transport = transport;
    }

    @Override
    public String getName() {
        return playerName;
    }

    @Override
    public void start() {
        playersThread = Thread.currentThread();
        while (!stopFlag) {
            Message message = receiveMessage();
            if (message != null) {
                Message reply = replyCreator.createReply(message);
                send(reply);
            } else {
                System.out.println("Message is not received. Retrying...");
            }
        }
        System.out.println("Player " + getName() + " stopped");
    }

    @Override
    public void stop() {
        stopFlag = true;
        if (playersThread != null) {
            playersThread.interrupt();
        }
    }

    @Override
    public void send(Message message) {
        transport.send(new Parcel<>(opponentName, message));
    }

    @Override
    public Message receiveMessage() {
        Parcel<Message> pack = transport.receive(getName());
        return pack != null ? pack.getBody() : null;
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
