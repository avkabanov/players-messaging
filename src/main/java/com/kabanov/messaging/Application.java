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
import com.kabanov.messaging.player.EventListeningPlayer;
import com.kabanov.messaging.player.InitiatorPlayer;
import com.kabanov.messaging.player.RespondingPlayer;
import com.kabanov.messaging.transport.ThreadsPackageTransport;

/**
 * @author Kabanov Alexey
 */
public class Application {

    private static ExecutorService service = Executors.newFixedThreadPool(2 );


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ThreadsPackageTransport threadsPackageTransport = new ThreadsPackageTransport();
        EventTransport eventTransport = new ThreadsEventTransport();
        
        EventListeningPlayer initiatorPlayer = new InitiatorPlayer(new RandomMessageCreator(), threadsPackageTransport);
        EventListeningPlayer respondingPlayer = new RespondingPlayer(new IncrementingReplyCreator(), threadsPackageTransport);
        
        initiatorPlayer.setOpponentName(respondingPlayer.getName());
        respondingPlayer.setOpponentName(initiatorPlayer.getName());

        eventTransport.register(initiatorPlayer.getName(), initiatorPlayer);
        eventTransport.register(respondingPlayer.getName(), respondingPlayer);
        
        Future<?> initiatorPlayerFuture = service.submit(initiatorPlayer::run);
        service.submit(respondingPlayer::run);

        initiatorPlayerFuture.get();

        eventTransport.sendEvent(respondingPlayer.getName(), Event.STOP);

        service.shutdown();
    }
}
