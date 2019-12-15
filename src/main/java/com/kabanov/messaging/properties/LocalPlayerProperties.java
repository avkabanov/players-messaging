package com.kabanov.messaging.properties;

import java.util.Properties;

import com.kabanov.messaging.di.PlayerType;

/**
 * @author Kabanov Alexey
 */
public class LocalPlayerProperties {
    private final int eventPort;
    PlayerType playerType;
    int messagePort;
    String name;
    String opponentName;

    public LocalPlayerProperties(Properties properties) {
        playerType = PlayerType.valueOf(properties.getProperty("local_player/type"));;
        messagePort = Integer.parseInt(properties.getProperty("local_player/message_port"));
        eventPort = Integer.parseInt(properties.getProperty("local_player/event_port"));
        name = properties.getProperty("local_player/name");
        opponentName = properties.getProperty("local_player/opponent_name");
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public int getMessagePort() {
        return messagePort;
    }

    public void setMessagePort(int messagePort) {
        this.messagePort = messagePort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public int getEventPort() {
        return eventPort;
    }
}
