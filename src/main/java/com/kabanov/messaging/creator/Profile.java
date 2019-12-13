package com.kabanov.messaging.creator;

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
