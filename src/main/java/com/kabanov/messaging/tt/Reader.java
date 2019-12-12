package com.kabanov.messaging.tt;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Kabanov Alexey
 */
public class Reader {
    
    private MessagingSystem messagingSystem = new MessagingSystem();
    private boolean quitting = false;
    private Thread currentThread;
    
    public void start() {
        currentThread = Thread.currentThread();
        
        while (!quitting) {
            currentThread.interrupt();
            String result = receive();
            if (result != null) {
                System.out.println(result);
            }
        }
    }
    
    public void stop() {
        quitting = true;
        currentThread.interrupt();
    }
    
    private String receive() {
        return messagingSystem.receive();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinTask<?> task = new ForkJoinPool().submit(new Reader()::start);
        task.get();
    }
}



class MessagingSystem {
    
    private BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    
    public void put(String message) throws InterruptedException {
        queue.put(message);
    }
    
    public String receive() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            return null;
        }
    }
}
