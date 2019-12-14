package com.kabanov.messaging.di;

import com.kabanov.messaging.di.factory.ObjectsFactory;
import com.kabanov.messaging.di.factory.SocketObjectsFactory;
import com.kabanov.messaging.di.factory.ThreadObjectsFactory;

/**
 * @author Kabanov Alexey
 */
public enum Profile {
    SOCKET_COMMUNICATION {
        @Override
        public ObjectsFactory createObjectsFactory() {
            return new SocketObjectsFactory();
        }
    },
    IN_MEMORY_QUEUE_COMMUNICATION {
        @Override
        public ObjectsFactory createObjectsFactory() {
            return new ThreadObjectsFactory();
        }
    };

    public abstract ObjectsFactory createObjectsFactory();
}
