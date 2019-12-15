package com.kabanov.messaging.utils;

import java.net.InetSocketAddress;

import javax.annotation.Nonnull;

/**
 * @author Kabanov Alexey
 */
public class SocketUtils {

    /**
     * @param string takes host and port in <host>:<port> format
     * @return InetSocketAddress representation 
     */
    public static InetSocketAddress parseString(@Nonnull String string) {
        String[] result = string.split(":");
        String host = result[0];
        int port = Integer.parseInt(result[1]);
        
        return new InetSocketAddress(host, port);
    }
}
