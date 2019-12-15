package com.kabanov.messaging.player;

import com.kabanov.messaging.player.messages.Message;

/**
 * @author Kabanov Alexey
 */
public interface Player {

    String getName();

    void start();
    
    void stop();

    void send(Message message);

    Message receiveMessage(); 
}
