package com.kabanov.messaging.di.factory;

import java.io.IOException;

import com.kabanov.messaging.di.PlayerType;
import com.kabanov.messaging.player.Player;
import com.kabanov.messaging.player.PlayerImpl;
import com.kabanov.messaging.player.Receiver;
import com.kabanov.messaging.player.Sender;
import com.kabanov.messaging.player.behavior.InitiativeBehavior;
import com.kabanov.messaging.player.behavior.RecipientBehavior;
import com.kabanov.messaging.player.messages.IncrementingReplyCreator;
import com.kabanov.messaging.player.messages.RandomMessageCreator;
import com.kabanov.messaging.properties.InitiatorPlayerProperties;

/**
 * @author Kabanov Alexey
 */
public abstract class BaseObjectsFactory implements ObjectsFactory {

    private InitiatorPlayerProperties initiatorPlayerProperties;

    protected BaseObjectsFactory() {
        try {
            initiatorPlayerProperties = InitiatorPlayerProperties.loadFrom("initiator_player.properties");
        } catch (IOException e) {
            // since we hardcode the properties name - let JMV to handle that exception
            throw new RuntimeException(e);
        }
    }

    @Override
    public Player createPlayer(String playerName, String opponentName, PlayerType playerType) {
        switch (playerType) {
            case INITIATOR:
                return createInitiatorPlayer(playerName, opponentName);
            case RESPONDING:
                return createRespondingPlayer(playerName, opponentName);
            default:
                throw new IllegalArgumentException(playerType + " not implemented");
        }
    }

    public InitiatorPlayerProperties getInitiatorPlayerProperties() {
        return initiatorPlayerProperties;
    }

    public Player createInitiatorPlayer(String playerName, String opponentName) {
        return new PlayerImpl(playerName,
                new InitiativeBehavior(
                        new Sender(getParcelTransport()),
                        new Receiver(playerName, getParcelTransport()),
                        new RandomMessageCreator(),
                        opponentName,
                        getInitiatorPlayerProperties()));
    }

    public Player createRespondingPlayer(String playerName, String opponentName) {
        return new PlayerImpl(playerName,
                new RecipientBehavior(
                        new Sender(getParcelTransport()),
                        new Receiver(playerName, getParcelTransport()),
                        new IncrementingReplyCreator(),
                        opponentName)
        );
    }

}
