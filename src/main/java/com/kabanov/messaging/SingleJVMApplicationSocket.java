package com.kabanov.messaging;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.kabanov.messaging.connection.ClientServerSocket;
import com.kabanov.messaging.connection.SocketConnector;
import com.kabanov.messaging.di.Profile;
import com.kabanov.messaging.di.factory.ObjectsFactory;
import com.kabanov.messaging.event.EventTransport;
import com.kabanov.messaging.event.SocketEventTransport;
import com.kabanov.messaging.player.EventListeningPlayer;
import com.kabanov.messaging.properties.LocalPlayerProperties;
import com.kabanov.messaging.properties.PlayersProperties;
import com.kabanov.messaging.transport.SocketTransport;
import com.kabanov.messaging.transport.Transport;

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
        Transport packsTransport = objectsFactory.getTransport();
        EventTransport eventTransport = objectsFactory.getEventTransport();

        LocalPlayerProperties localPlayerProperties = playersProperties.getLocalPlayerProperties();
        EventListeningPlayer localPlayer = objectsFactory.createPlayer(
                localPlayerProperties.getName(),
                localPlayerProperties.getOpponentName(),
                localPlayerProperties.getPlayerType());

        subscribeForMessages((SocketTransport) packsTransport, playersProperties);
        
        subscribeForEvents((SocketEventTransport) eventTransport, playersProperties, localPlayer);

        Future<?> playerFuture = service.submit(localPlayer::start);

        playerFuture.get();

        eventTransport.sendEvent(respondingPlayer.getName(), Event.STOP);

        // tear down: shutdown service
        service.shutdown();
    }

    private static void subscribeForEvents(SocketEventTransport socketEventTransport, PlayersProperties playersProperties, EventListeningPlayer localPlayer) throws ExecutionException, InterruptedException, IOException {
        ClientServerSocket clientServerSocket = SocketConnector.openClientAndServerSocket(
                playersProperties.getRemotePlayerProperties().getEventAddress(),
                playersProperties.getLocalPlayerProperties().getEventPort());
        
        socketEventTransport.register(playersProperties.getLocalPlayerProperties().getName(), localPlayer, 
                clientServerSocket.getServerSocket());
        
    }

    private static void subscribeForMessages(SocketTransport packsTransport,
                                             PlayersProperties playersProperties) throws IOException, ExecutionException, InterruptedException {

        ClientServerSocket clientServerSocket = SocketConnector.openClientAndServerSocket(
                playersProperties.getRemotePlayerProperties().getMessageAddress(),
                playersProperties.getLocalPlayerProperties().getMessagePort());

        packsTransport.register(playersProperties.getLocalPlayerProperties().getName(), 
                clientServerSocket.getServerSocket());
        packsTransport.register(playersProperties.getRemotePlayerProperties().getName(),
                clientServerSocket.getClientSocket());
    }

}
