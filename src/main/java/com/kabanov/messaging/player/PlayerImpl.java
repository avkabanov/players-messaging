package com.kabanov.messaging.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kabanov.messaging.player.behavior.Behavior;

/**
 * @author Kabanov Alexey
 */
public class PlayerImpl implements Player {

    private static final Logger logger = LoggerFactory.getLogger(PlayerImpl.class);

    private String playerName;
    private Behavior behavior;
    private Thread playersThread;
                      
    public PlayerImpl(String playerName, Behavior behavior) {
        this.playerName = playerName;
        this.behavior = behavior;
    }

    @Override
    public String getName() {
        return playerName;
    }

    @Override
    public void start() {
        playersThread = Thread.currentThread();
        behavior.invoke();
        logger.info("Player " + getName() + " stopped");
    }

    @Override
    public void stop() {
        logger.info("Stopping player " + getName());
        behavior.stop();
        if (playersThread != null) {
            playersThread.interrupt();
        }
    }
}
