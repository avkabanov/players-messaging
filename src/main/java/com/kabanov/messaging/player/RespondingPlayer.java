package com.kabanov.messaging.player;

import com.kabanov.messaging.event.Event;
import com.kabanov.messaging.messages.Message;
import com.kabanov.messaging.messages.ReplyCreator;
import com.kabanov.messaging.parcel.Parcel;
import com.kabanov.messaging.parcel.ParcelTransport;

/**
 * @author Kabanov Alexey
 */
public class RespondingPlayer implements EventListeningPlayer {
    private ReplyCreator replyCreator;
    private ParcelTransport parcelTransport;
    private volatile boolean stopFlag = false;
    private String opponentName;
    private Thread playersThread;

    public RespondingPlayer(ReplyCreator replyCreator,
                            ParcelTransport parcelTransport) {
        this.replyCreator = replyCreator;
        this.parcelTransport = parcelTransport;
    }

    @Override
    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    @Override
    public String getName() {
        return "Player2";
    }

    @Override
    public void start() {
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
        stopFlag = true;
        if (playersThread != null) {
            playersThread.interrupt();
        }
    }

    @Override
    public void send(Message message) {
        parcelTransport.send(new Parcel(opponentName, message));
    }

    @Override
    public Message receiveMessage() {
        Parcel pack = parcelTransport.receive(getName());
        return pack != null ? pack.getMessage() : null;
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
