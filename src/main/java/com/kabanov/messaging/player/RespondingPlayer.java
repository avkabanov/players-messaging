package com.kabanov.messaging.player;

import com.kabanov.messaging.messages.Message;
import com.kabanov.messaging.messages.ReplyCreator;
import com.kabanov.messaging.parcel.ParcelTransport;
import com.kabanov.messaging.transport.Parcel;

/**
 * @author Kabanov Alexey
 */
public class RespondingPlayer implements Player {
    private String playerName;
    private ReplyCreator replyCreator;
    private ParcelTransport parcelTransport;
    private volatile boolean stopFlag = false;
    private String opponentName;
    private Thread playersThread;

    public RespondingPlayer(String playerName, String opponentName, ReplyCreator replyCreator,
                            ParcelTransport parcelTransport) {
        this.playerName = playerName;
        this.opponentName = opponentName;
        this.replyCreator = replyCreator;
        this.parcelTransport = parcelTransport;
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
                System.out.println("stopping");
                stop();
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
        return pack != null ? pack.getBody() : null;
    }
}
