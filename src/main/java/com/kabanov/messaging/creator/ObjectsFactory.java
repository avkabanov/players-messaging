package com.kabanov.messaging.creator;

import com.kabanov.messaging.event.EventTransport;
import com.kabanov.messaging.parcel.ParcelTransport;
import com.kabanov.messaging.player.EventListeningPlayer;

/**
 * @author Kabanov Alexey
 */
public interface ObjectsFactory {
    ParcelTransport getParcelTransport();

    EventTransport createEventTransport();

    EventListeningPlayer createPlayer(String playerName, PlayerType playerType);
}
