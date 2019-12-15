package com.kabanov.messaging.di.factory;

import com.kabanov.messaging.di.PlayerType;
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
    public EventListeningPlayer createPlayer(String playerName, String opponentName, PlayerType playerType) {
        switch (playerType) {
            case INITIATOR:
                return createInitiatorPlayer(playerName, opponentName);
            case RESPONDING:
                return createRespondingPlayer(playerName, opponentName);
            default:
                throw new IllegalArgumentException(playerType + " not implemented");
        }
    }

    public EventListeningPlayer createInitiatorPlayer(String playerName, String opponentName) {
        return new InitiatorPlayer(playerName, opponentName, new RandomMessageCreator(), getParcelTransport());
    }

    public EventListeningPlayer createRespondingPlayer(String playerName, String opponentName) {
        return new RespondingPlayer(playerName, opponentName, new IncrementingReplyCreator(), getParcelTransport());
    }
}
