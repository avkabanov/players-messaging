package com.kabanov.messaging.transport;

import javax.annotation.Nullable;

/**
 * @author Kabanov Alexey
 */
public interface PackageTransport {
    
    void send(Package message);

    @Nullable
    Package receive(String recipient);
    
}
