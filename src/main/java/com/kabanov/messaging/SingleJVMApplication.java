package com.kabanov.messaging;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.kabanov.messaging.di.PlayerType;
import com.kabanov.messaging.di.Profile;
import com.kabanov.messaging.di.factory.ObjectsFactory;
import com.kabanov.messaging.event.Event;
import com.kabanov.messaging.event.EventTransport;
import com.kabanov.messaging.event.ThreadsEventTransport;
import com.kabanov.messaging.player.EventListeningPlayer;

/**
 * @author Kabanov Alexey
 */
public class SingleJVMApplication {

    public static final Profile PROFILE = Profile.IN_MEMORY_QUEUE_COMMUNICATION;
    private ObjectsFactory objectFactory = PROFILE.createObjectsFactory();
    

    private static ExecutorService service = Executors.newFixedThreadPool(2 );

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new SingleJVMApplication().run();
    }

    public void run() throws ExecutionException, InterruptedException {
        // create transport
        EventTransport eventTransport = new ThreadsEventTransport();
        
        // create players
        EventListeningPlayer initiatorPlayer = objectFactory.createPlayer("Player1", "Player2", PlayerType.INITIATOR);
        EventListeningPlayer respondingPlayer = objectFactory.createPlayer("Player2", "Player1", PlayerType.RESPONDING);


        // subscribe players for events
        eventTransport.register(initiatorPlayer.getName(), initiatorPlayer);
        eventTransport.register(respondingPlayer.getName(), respondingPlayer);

        // run two players simultaneously
        Future<?> initiatorPlayerFuture = service.submit(initiatorPlayer::start);
        service.submit(respondingPlayer::start);

        // wait for initiator to complete 
        initiatorPlayerFuture.get();

        // stopping players 
        eventTransport.sendEvent(respondingPlayer.getName(), Event.STOP);

        // tear down: shutdown service
        service.shutdown();
    }
}
