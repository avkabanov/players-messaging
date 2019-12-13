package com.kabanov.messaging.parcel;

import java.io.IOException;
import java.net.Socket;

import javax.annotation.Nullable;

/**
 * @author Kabanov Alexey
 */
public interface ParcelTransport {

    void register(String name, Socket socket) throws IOException;

    void send(Parcel message);

    @Nullable
    Parcel receive(String recipient);
    
}
