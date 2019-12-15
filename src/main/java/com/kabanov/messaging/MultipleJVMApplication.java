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
import com.kabanov.messaging.player.Player;
import com.kabanov.messaging.properties.LocalPlayerProperties;
import com.kabanov.messaging.properties.PlayersProperties;

/**
 * @author Kabanov Alexey
 */
// TODO add logger
public class MultipleJVMApplication {

    public static final Profile PROFILE = Profile.SOCKET_COMMUNICATION;
    private ObjectsFactory objectsFactory = PROFILE.createObjectsFactory();
    
    private static ExecutorService service = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        new MultipleJVMApplication().run(args[0]);
    }

    private void run(String propertiesFile) throws IOException, ExecutionException, InterruptedException {
        PlayersProperties playersProperties = PlayersProperties.loadFrom(propertiesFile);

        // create transport
        ParcelTransport packsTransport = objectsFactory.getParcelTransport();

        LocalPlayerProperties localPlayerProperties = playersProperties.getLocalPlayerProperties();
        Player localPlayer = objectsFactory.createPlayer(
                localPlayerProperties.getName(),
                localPlayerProperties.getOpponentName(),
                localPlayerProperties.getPlayerType());

        registerPlayers((SocketParcelTransport)packsTransport, playersProperties);

        service.submit(localPlayer::start);

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
