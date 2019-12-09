package com.kabanov.messaging.player;

import com.kabanov.messaging.Event;
import com.kabanov.messaging.messages.Message;

/**
 * @author Kabanov Alexey
 */
public interface Player {

    void setOpponentName(String opponentName);

    String getName();

    void run();
    
    void stop();

    void send(Message message);

    Message receiveMessage();
    
    void onEventReceived(Event event);
}
