package com.kabanov.messaging;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.kabanov.messaging.event.Event;
import com.kabanov.messaging.event.EventTransport;
import com.kabanov.messaging.event.ThreadsEventTransport;
import com.kabanov.messaging.messages.IncrementingReplyCreator;
import com.kabanov.messaging.messages.RandomMessageCreator;
import com.kabanov.messaging.parcel.ThreadsParcelTransport;
import com.kabanov.messaging.player.EventListeningPlayer;
import com.kabanov.messaging.player.InitiatorPlayer;
import com.kabanov.messaging.player.RespondingPlayer;

/**
 * @author Kabanov Alexey
 */
public class SingleJVMApplication {

    private static ExecutorService service = Executors.newFixedThreadPool(2 );


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // create transport
        ThreadsParcelTransport threadsPackageTransport = new ThreadsParcelTransport();
        EventTransport eventTransport = new ThreadsEventTransport();
        
        // create players
        EventListeningPlayer initiatorPlayer = new InitiatorPlayer(new RandomMessageCreator(), threadsPackageTransport);
        EventListeningPlayer respondingPlayer = new RespondingPlayer(new IncrementingReplyCreator(), threadsPackageTransport);
        
        // initialize players
        initiatorPlayer.setOpponentName(respondingPlayer.getName());
        respondingPlayer.setOpponentName(initiatorPlayer.getName());

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
