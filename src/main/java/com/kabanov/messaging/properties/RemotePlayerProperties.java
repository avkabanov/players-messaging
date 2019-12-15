package com.kabanov.messaging.properties;

import java.util.Properties;

/**
 * @author Kabanov Alexey
 */
public class RemotePlayerProperties {
    String name;
    String host;
    int port;

    public RemotePlayerProperties(Properties properties) {
        port = Integer.parseInt(properties.getProperty("remote_player/port"));
        name = properties.getProperty("remote_player/name");
        host = properties.getProperty("remote_player/host");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
