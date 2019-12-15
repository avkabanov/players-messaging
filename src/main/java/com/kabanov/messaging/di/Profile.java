package com.kabanov.messaging.di;

import com.kabanov.messaging.di.factory.InMemoryObjectsFactory;
import com.kabanov.messaging.di.factory.ObjectsFactory;
import com.kabanov.messaging.di.factory.SocketObjectsFactory;

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
            return new InMemoryObjectsFactory();
        }
    };

    public abstract ObjectsFactory createObjectsFactory();
}
