package com.kabanov.messaging.transport;

/**
 * @author Kabanov Alexey
 */
public interface PackageTransport {
    
    void send(Package message);

    Package receive(String recipient);
}
