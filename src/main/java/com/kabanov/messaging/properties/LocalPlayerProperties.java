package com.kabanov.messaging.properties;

import java.util.Properties;

import com.kabanov.messaging.creator.PlayerType;

/**
 * @author Kabanov Alexey
 */
public class LocalPlayerProperties {
    PlayerType playerType;
    int port;
    String name;
    String opponentName;

    public LocalPlayerProperties(Properties properties) {
        playerType = PlayerType.valueOf(properties.getProperty("local_player/type"));;
        port = Integer.parseInt(properties.getProperty("local_player/port"));
        name = properties.getProperty("local_player/name");
        opponentName = properties.getProperty("local_player/opponent_name");
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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
}
