package com.kabanov.messaging;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.kabanov.messaging.connection.ClientServerSocket;
import com.kabanov.messaging.connection.SocketConnector;
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

        int localPort = playersProperties.getLocalPlayerProperties().getPort();
        String remoteHost = playersProperties.getRemotePlayerProperties().getHost();
        int remotePort = playersProperties.getRemotePlayerProperties().getPort();

        ClientServerSocket clientServerSocket = SocketConnector
                .openClientAndServerSocket(new InetSocketAddress(remoteHost, remotePort), localPort);
        
        packsTransport.register(playersProperties.getLocalPlayerProperties().getName(), clientServerSocket.getServerSocket());
        packsTransport.register(playersProperties.getRemotePlayerProperties().getName(), clientServerSocket.getClientSocket());
    }
}
