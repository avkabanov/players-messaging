package com.kabanov.messaging;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;

import com.kabanov.messaging.event.Event;
import com.kabanov.messaging.event.EventTransport;
import com.kabanov.messaging.event.ThreadsEventTransport;
import com.kabanov.messaging.messages.IncrementingReplyCreator;
import com.kabanov.messaging.messages.RandomMessageCreator;
import com.kabanov.messaging.parcel.SocketParcelTransport;
import com.kabanov.messaging.player.EventListeningPlayer;
import com.kabanov.messaging.player.InitiatorPlayer;
import com.kabanov.messaging.player.RespondingPlayer;

/**
 * @author Kabanov Alexey
 */
public class SingleJVMApplicationSocket {

    private static ExecutorService service = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

        // create transport
        SocketParcelTransport packsTransport = new SocketParcelTransport();
        EventTransport eventTransport = new ThreadsEventTransport();

        // create players
        EventListeningPlayer initiatorPlayer = new InitiatorPlayer(new RandomMessageCreator(), packsTransport);
        EventListeningPlayer respondingPlayer = new RespondingPlayer(new IncrementingReplyCreator(), packsTransport);

        // initialize players
        initiatorPlayer.setOpponentName(respondingPlayer.getName());
        respondingPlayer.setOpponentName(initiatorPlayer.getName());

        registerPlayers(packsTransport);

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

    private static void registerPlayers(SocketParcelTransport packsTransport) throws IOException, ExecutionException, InterruptedException {
        // player1 socket 
        ForkJoinTask<Socket> future1 = ForkJoinPool.commonPool().submit(() -> {
            ServerSocket serverSocket = null;
            try {

                serverSocket = new ServerSocket(4444);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return serverSocket.accept();
        });

        // player2 socket 
        ForkJoinTask<Socket> future2 = ForkJoinPool.commonPool().submit(() -> createClient(4444));

        packsTransport.register("Player1", future1.get());
        packsTransport.register("Player2", future2.get());
    }

    private static void registerPlayer(SocketParcelTransport packsTransport,
                                       EventListeningPlayer player, int port, String opponentName,
                                       int opponentPort) throws IOException, ExecutionException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(port);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<?> future1 = executorService.submit(() -> {
            try {
                packsTransport.register(player.getName(), serverSocket.accept());
                System.out.println("Registered");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Future<?> future2 = executorService.submit(() -> {
            try {
                packsTransport.register(opponentName, createClient(opponentPort));
                System.out.println("Client created");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        future1.get();
        future2.get();

    }

    private static Socket createClient(int port) {
        do {
            try {
                Socket socket = new Socket("localhost", port);
                return socket;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (true);
    }

}
