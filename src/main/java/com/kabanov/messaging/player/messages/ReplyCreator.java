package com.kabanov.messaging.player.messages;

/**
 * @author Kabanov Alexey
 */
public interface ReplyCreator {
    
    Message createReply(Message message);
}
