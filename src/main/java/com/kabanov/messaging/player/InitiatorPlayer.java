package com.kabanov.messaging.player;

import com.kabanov.messaging.event.Event;
import com.kabanov.messaging.messages.Message;
import com.kabanov.messaging.messages.MessageCreator;
import com.kabanov.messaging.transport.Package;
import com.kabanov.messaging.transport.PackageTransport;

/**
 * @author Kabanov Alexey
 */
public class InitiatorPlayer implements EventListeningPlayer {

    private MessageCreator messageCreator;
    private PackageTransport packageTransport;
    private String opponentName;

    public InitiatorPlayer(MessageCreator messageCreator,
                           PackageTransport packageTransport) {
        this.messageCreator = messageCreator;
        this.packageTransport = packageTransport;
    }

    @Override
    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    @Override
    public String getName() {
        return "Initiative_player";
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Message message = messageCreator.createMessage();
            send(message);
            receiveMessage();
        }
        System.out.println("Player " + getName() + " stopped");
    }

    @Override
    public void stop() {
        // TODO fix stop method
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
