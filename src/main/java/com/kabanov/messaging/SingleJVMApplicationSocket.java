package com.kabanov.messaging;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import com.kabanov.messaging.di.Profile;
import com.kabanov.messaging.di.factory.ObjectsFactory;
import com.kabanov.messaging.parcel.ParcelTransport;
import com.kabanov.messaging.parcel.SocketParcelTransport;
import com.kabanov.messaging.player.EventListeningPlayer;
import com.kabanov.messaging.properties.LocalPlayerProperties;
import com.kabanov.messaging.properties.PlayersProperties;

/**
 * @author Kabanov Alexey
 */
public class SingleJVMApplicationSocket {

    public static final Profile PROFILE = Profile.SOCKET_COMMUNICATION;

    private static ExecutorService service = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

        String propertiesFile = args[0];

        PlayersProperties playersProperties = PlayersProperties.loadFrom(propertiesFile);
        ObjectsFactory objectsFactory = PROFILE.createObjectsFactory();

        // create transport
        ParcelTransport packsTransport = objectsFactory.getParcelTransport();
        //EventTransport eventTransport = objectsFactory.createEventTransport();

        LocalPlayerProperties localPlayerProperties = playersProperties.getLocalPlayerProperties();
        EventListeningPlayer localPlayer = objectsFactory.createPlayer(
                localPlayerProperties.getName(),
                localPlayerProperties.getOpponentName(),
                localPlayerProperties.getPlayerType());

        registerPlayers((SocketParcelTransport)packsTransport, playersProperties);

        // subscribe players for events
        //eventTransport.register(initiatorPlayer.getName(), initiatorPlayer);
        //eventTransport.register(respondingPlayer.getName(), respondingPlayer);

        // run two players simultaneously
        //Future<?> initiatorPlayerFuture = service.submit(initiatorPlayer::start);
        service.submit(localPlayer::start);

        // wait for initiator to complete 
        //initiatorPlayerFuture.get();

        // stopping players 
        //eventTransport.sendEvent(respondingPlayer.getName(), Event.STOP);

        // tear down: shutdown service
        service.shutdown();
    }

    private static void registerPlayers(SocketParcelTransport packsTransport,
                                        PlayersProperties playersProperties) throws IOException, ExecutionException, InterruptedException {
        // player1 socket 
        ForkJoinTask<Socket> localPlayerFuture = ForkJoinPool.commonPool().submit(() -> {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(playersProperties.getLocalPlayerProperties().getPort());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return serverSocket.accept();
        });

        // player2 socket 
        ForkJoinTask<Socket> opponentFuture = ForkJoinPool.commonPool()
                .submit(() -> createClient(playersProperties.getRemotePlayerProperties().getHost(),
                        playersProperties.getRemotePlayerProperties().getPort()));

        packsTransport.register(playersProperties.getLocalPlayerProperties().getName(), localPlayerFuture.get());
        packsTransport.register(playersProperties.getRemotePlayerProperties().getName(), opponentFuture.get());
    }

    private static Socket createClient(String host, int port) {
        do {
            try {
                return new Socket(host, port);
            } catch (IOException e) {
                System.out.println("Error creating socket: " + e.getMessage());
            }
        } while (true);
    }

}
