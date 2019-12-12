package com.kabanov.messaging.parcel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.Nullable;

/**
 * @author Kabanov Alexey
 */
public class ThreadsParcelTransport implements ParcelTransport {

    private ConcurrentHashMap<String, BlockingQueue<Parcel>> parcelList = new ConcurrentHashMap<>();
    
    @Override
    public void send(Parcel message) {
        System.out.println(
                "Message sent for " + message.getReceiverName() + " with text: " + message.getMessage().getText());
        parcelList.computeIfAbsent(message.getReceiverName(), (n) -> new LinkedBlockingQueue<>()).add(message);
    }

    @Nullable
    @Override
    public Parcel receive(String recipient) {
        Parcel poll;
        try {
            poll = parcelList.computeIfAbsent(recipient, (n) -> new LinkedBlockingQueue<>()).take();
        } catch (InterruptedException e) {
            return null;
        }
        System.out.println("Message received by " + recipient + " with text: " + poll.getMessage().getText());
        return poll;
    }
}
