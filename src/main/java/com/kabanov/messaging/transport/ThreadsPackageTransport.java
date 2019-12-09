package com.kabanov.messaging.transport;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.Nullable;

/**
 * @author Kabanov Alexey
 */
public class ThreadsPackageTransport implements PackageTransport {

    private ConcurrentHashMap<String, BlockingQueue<Package>> packages = new ConcurrentHashMap<>();
    
    @Override
    public void send(Package message) {
        System.out.println(
                "Message sent for " + message.getReceiverName() + " with text: " + message.getMessage().getText());
        packages.computeIfAbsent(message.getReceiverName(), (n) -> new LinkedBlockingQueue<>()).add(message);
    }

    @Nullable
    @Override
    public Package receive(String recipient) {
        Package poll = null;
        try {
            poll = packages.computeIfAbsent(recipient, (n) -> new LinkedBlockingQueue<>()).take();
        } catch (InterruptedException e) {
            return null;
        }
        System.out.println("Message received by " + recipient + " with text: " + poll.getMessage().getText());
        return poll;
    }
}
