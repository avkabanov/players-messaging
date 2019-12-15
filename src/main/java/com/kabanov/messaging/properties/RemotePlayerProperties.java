package com.kabanov.messaging.properties;

import java.net.InetSocketAddress;
import java.util.Properties;

import com.kabanov.messaging.utils.SocketUtils;

/**
 * @author Kabanov Alexey
 */
public class RemotePlayerProperties {
    private String name;
    private String host;
    private int port;

    private final InetSocketAddress eventAddress;
    private InetSocketAddress messageAddress;

    public RemotePlayerProperties(Properties properties) {
        port = Integer.parseInt(properties.getProperty("remote_player/port"));
        name = properties.getProperty("remote_player/name");
        host = properties.getProperty("remote_player/host");  
        messageAddress = SocketUtils.parseString("remote_player/message_address");
        eventAddress = SocketUtils.parseString("remote_player/event_address");
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

    public InetSocketAddress getEventAddress() {
        return eventAddress;
    }

    public InetSocketAddress getMessageAddress() {
        return messageAddress;
    }
}
