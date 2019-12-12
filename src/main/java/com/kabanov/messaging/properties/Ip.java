package com.kabanov.messaging.properties;

/**
 * @author Kabanov Alexey
 */
public class Ip {
    String hostname;
    int port;

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public boolean isLocalhost() {
        return true;
    }
}
