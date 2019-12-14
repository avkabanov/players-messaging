package com.kabanov.messaging.parcel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.Nullable;

import com.kabanov.messaging.transport.Parcel;

/**
 * @author Kabanov Alexey
 */
public class InMemoryParcelTransport implements ParcelTransport {

    private ConcurrentHashMap<String, BlockingQueue<Parcel>> parcelList = new ConcurrentHashMap<>();
    
    @Override
    public void send(Parcel parcel) {
        System.out.println(
                "Message sent for " + parcel.getReceiverName() + " with text: " + parcel.getBody().getText());
        parcelList.computeIfAbsent(parcel.getReceiverName(), (n) -> new LinkedBlockingQueue<>()).add(parcel);
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
        System.out.println("Message received by " + recipient + " with text: " + poll.getBody().getText());
        return poll;
    }
}
