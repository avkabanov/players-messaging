package com.kabanov.messaging.properties;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Kabanov Alexey
 */
public class PlayersProperties {

    private Properties properties = new Properties();
    private LocalPlayerProperties localPlayerProperties;
    private RemotePlayerProperties remotePlayerProperties;

    private PlayersProperties(String fileName) throws IOException {
        String path = Thread.currentThread().getContextClassLoader().getResource(fileName).getPath();
        properties.load(new FileReader(path));    
        localPlayerProperties = new LocalPlayerProperties(properties);
        remotePlayerProperties = new RemotePlayerProperties(properties);
    }

    public static PlayersProperties loadFrom(String propertiesName) throws IOException {
        return new PlayersProperties(propertiesName);
    }

    public LocalPlayerProperties getLocalPlayerProperties() {
        return localPlayerProperties;
    }

    public RemotePlayerProperties getRemotePlayerProperties() {
        return remotePlayerProperties;
    }
}
