package com.kabanov.messaging.di.factory;

import com.kabanov.messaging.di.PlayerType;
import com.kabanov.messaging.event.EventTransport;
import com.kabanov.messaging.player.EventListeningPlayer;
import com.kabanov.messaging.transport.Transport;

/**
 * @author Kabanov Alexey
 */
public interface ObjectsFactory {
    Transport getTransport();

    EventTransport getEventTransport();

    EventListeningPlayer createPlayer(String playerName, String opponentName, PlayerType playerType);
}
