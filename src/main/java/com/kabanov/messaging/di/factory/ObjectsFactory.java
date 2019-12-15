package com.kabanov.messaging.di.factory;

import com.kabanov.messaging.di.PlayerType;
import com.kabanov.messaging.event.EventTransport;
import com.kabanov.messaging.parcel.ParcelTransport;
import com.kabanov.messaging.player.EventListeningPlayer;

/**
 * @author Kabanov Alexey
 */
public interface ObjectsFactory {
    ParcelTransport getParcelTransport();

    EventTransport getEventTransport();

    EventListeningPlayer createPlayer(String playerName, String opponentName, PlayerType playerType);
}
