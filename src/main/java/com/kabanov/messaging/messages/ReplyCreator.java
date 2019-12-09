package com.kabanov.messaging.messages;

/**
 * @author Kabanov Alexey
 */
public interface ReplyCreator {
    
    Message createReply(Message message);
}
