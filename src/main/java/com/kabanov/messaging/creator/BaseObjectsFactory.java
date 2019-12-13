package com.kabanov.messaging.creator;

import com.kabanov.messaging.messages.IncrementingReplyCreator;
import com.kabanov.messaging.messages.RandomMessageCreator;
import com.kabanov.messaging.player.EventListeningPlayer;
import com.kabanov.messaging.player.InitiatorPlayer;
import com.kabanov.messaging.player.RespondingPlayer;

/**
 * @author Kabanov Alexey
 */
public abstract class BaseObjectsFactory implements ObjectsFactory {

    @Override
    public EventListeningPlayer createPlayer(String playerName, PlayerType playerType) {
        switch (playerType) {
            case INITIATOR:
                return createInitiatorPlayer(playerName);
            case RESPONDING:
                return createRespondingPlayer(playerName);
            default:
                throw new IllegalArgumentException(playerType + " not implemented");
        }
    }

    public EventListeningPlayer createInitiatorPlayer(String playerName) {
        return new InitiatorPlayer(playerName, new RandomMessageCreator(), getParcelTransport());
    }

    public EventListeningPlayer createRespondingPlayer(String playerName) {
        return new RespondingPlayer(playerName, new IncrementingReplyCreator(), getParcelTransport());
    }
}
