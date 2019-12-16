package com.kabanov.messaging.properties;

import java.util.Properties;

import com.kabanov.messaging.di.PlayerType;

/**
 * @author Kabanov Alexey
 */
public class LocalPlayerProperties {
    private PlayerType playerType;
    private int port;
    private String name;
    private String opponentName;

    public LocalPlayerProperties(Properties properties) {
        playerType = PlayerType.parseString(properties.getProperty("local_player/type"));;
        port = Integer.parseInt(properties.getProperty("local_player/port"));
        name = properties.getProperty("local_player/name");
        opponentName = properties.getProperty("local_player/opponent_name");
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }

    public String getOpponentName() {
        return opponentName;
    }

}
