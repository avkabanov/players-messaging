package com.kabanov.messaging.di.factory;

import com.kabanov.messaging.di.PlayerType;
import com.kabanov.messaging.parcel.ParcelTransport;
import com.kabanov.messaging.player.Player;

/**
 * @author Kabanov Alexey
 */
public interface ObjectsFactory {
    ParcelTransport getParcelTransport();

    Player createPlayer(String playerName, String opponentName, PlayerType playerType);
}
