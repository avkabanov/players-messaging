package com.kabanov.messaging.transport;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.Nullable;

/**
 * @author Kabanov Alexey
 */
public class InMemoryTransport<D> implements Transport<Parcel<D>, D> {

    private ConcurrentHashMap<String, BlockingQueue<Parcel<D>>> parcelList = new ConcurrentHashMap<>();
    
    @Override
    public void send(Parcel<D> parcel) {
        System.out.println(
                "Message sent for " + parcel.getReceiverName() + " with text: " + parcel.getBody());
        parcelList.computeIfAbsent(parcel.getReceiverName(), (n) -> new LinkedBlockingQueue<>()).add(parcel);
    }

    @Nullable
    @Override
    public Parcel<D> receive(String recipient) {
        Parcel<D> poll;
        try {
            poll = parcelList.computeIfAbsent(recipient, (n) -> new LinkedBlockingQueue<>()).take();
        } catch (InterruptedException e) {
            return null;
        }
        System.out.println("Message received by " + recipient + " with text: " + poll.getBody());
        return poll;
    }
}
