package com.kabanov.messaging;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.kabanov.messaging.di.PlayerType;
import com.kabanov.messaging.di.Profile;
import com.kabanov.messaging.di.factory.ObjectsFactory;
import com.kabanov.messaging.player.Player;

/**
 * @author Kabanov Alexey
 */
public class SingleJVMApplication {

    // TODO run properties validator
    // TODO Think about merging behavior
    public static final Profile PROFILE = Profile.IN_MEMORY_QUEUE_COMMUNICATION;
    private ObjectsFactory objectFactory = PROFILE.createObjectsFactory();
    
    private static ExecutorService service = Executors.newFixedThreadPool(2 );

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new SingleJVMApplication().run();
    }

    public void run() throws ExecutionException, InterruptedException {
        
        // create players
        Player initiatorPlayer = objectFactory.createPlayer("Player1", "Player2", PlayerType.INITIATOR);
        Player respondingPlayer = objectFactory.createPlayer("Player2", "Player1", PlayerType.RESPONDING);

        // run two players simultaneously
        Future<?> initiatorPlayerFuture = service.submit(initiatorPlayer::start);
        service.submit(respondingPlayer::start);

        // wait for initiator to complete 
        initiatorPlayerFuture.get();

        respondingPlayer.stop();
        
        // tear down: shutdown service
        service.shutdown();
    }
}
