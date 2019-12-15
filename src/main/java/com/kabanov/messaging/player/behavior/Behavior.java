package com.kabanov.messaging.player.behavior;

/**
 * @author Kabanov Alexey
 */
public interface Behavior {

    /**
     * invoke behavior until stop method will be invoked
     */
    void invoke();

    void stop();
}
