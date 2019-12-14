package com.kabanov.messaging.transport;

import java.io.Serializable;

/**
 * @author Kabanov Alexey
 */
public interface Transferable<T> extends Serializable {
    
    String getReceiverName();
    
    T getBody();
}
