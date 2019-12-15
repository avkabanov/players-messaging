package com.kabanov.messaging.player;

import com.kabanov.messaging.messages.Message;
import com.kabanov.messaging.messages.MessageCreator;
import com.kabanov.messaging.parcel.ParcelTransport;
import com.kabanov.messaging.transport.Parcel;

/**
 * @author Kabanov Alexey
 */
public class InitiatorPlayer implements Player {

    private String playerName;
    private MessageCreator messageCreator;
    private ParcelTransport parcelTransport;
    private String opponentName;
    private volatile boolean stopFlag;
    private Thread playersThread;

    public InitiatorPlayer(String playerName, String opponentName, MessageCreator messageCreator,
                           ParcelTransport parcelTransport) {
        this.playerName = playerName;
        this.opponentName = opponentName;
        this.messageCreator = messageCreator;
        this.parcelTransport = parcelTransport;
    }
   
    @Override
    public String getName() {
        return playerName;
    }

    @Override
    public void start() {
        playersThread = Thread.currentThread();
        
        for (int i = 0; i < 10 && !stopFlag; i++) {
            Message message = messageCreator.createMessage();
            send(message);
            receiveMessage();
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
        return parcelTransport.receive(getName()).getBody();
    }
}
