package com.kabanov.messaging.properties;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * @author Kabanov Alexey
 */
public class PropertiesLoader {

    private PropertiesLoader() {
    }
    
    public static Properties loadFromResource(String fileNameInResource) throws IOException {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(fileNameInResource);
        if (resource == null) {
            throw new FileNotFoundException(fileNameInResource);
        }
        
        String path = resource.getPath();
        Properties properties = new Properties();
        properties.load(new FileReader(path));
        return properties;
    }
}
