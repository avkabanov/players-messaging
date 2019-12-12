package com.kabanov.messaging.player;

import com.kabanov.messaging.messages.Message;

/**
 * @author Kabanov Alexey
 */
public interface Player {

    void setOpponentName(String opponentName);

    String getName();

    void start();
    
    void stop();

    void send(Message message);

    Message receiveMessage(); 
}
