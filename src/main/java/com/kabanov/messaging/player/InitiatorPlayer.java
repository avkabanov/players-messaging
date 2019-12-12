package com.kabanov.messaging.player;

import com.kabanov.messaging.event.Event;
import com.kabanov.messaging.messages.Message;
import com.kabanov.messaging.messages.MessageCreator;
import com.kabanov.messaging.parcel.Parcel;
import com.kabanov.messaging.parcel.ParcelTransport;

/**
 * @author Kabanov Alexey
 */
public class InitiatorPlayer implements EventListeningPlayer {

    private MessageCreator messageCreator;
    private ParcelTransport parcelTransport;
    private String opponentName;

    public InitiatorPlayer(MessageCreator messageCreator,
                           ParcelTransport parcelTransport) {
        this.messageCreator = messageCreator;
        this.parcelTransport = parcelTransport;
    }

    @Override
    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    @Override
    public String getName() {
        return "Player1";
    }

    @Override
    public void start() {
        for (int i = 0; i < 10; i++) {
            Message message = messageCreator.createMessage();
            send(message);
            Message response = receiveMessage();
            System.out.println(response);
        }
        System.out.println("Player " + getName() + " stopped");
    }

    @Override
    public void stop() {
        // TODO fix stop method
    }

    @Override
    public void send(Message message) {
        parcelTransport.send(new Parcel(opponentName, message));
    }

    @Override
    public Message receiveMessage() {
        return parcelTransport.receive(getName()).getMessage();
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
