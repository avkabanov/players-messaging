package com.kabanov.messaging.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Kabanov Alexey
 */
public class PropertiesLoader {

    private PropertiesLoader() {
    }
    
    public static Properties loadFromResource(String fileNameInResource) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(fileNameInResource);
        Properties properties = new Properties();
        properties.load(is);
        
        return properties;
    }
}
