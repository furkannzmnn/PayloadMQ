package org.example.broker;

import org.example.data.Payload;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class BrokerCluster {
    protected final BlockingQueue<String> queue;
    protected final Queue<String> registerMethods;


    public BrokerCluster() {
        this.registerMethods = new ConcurrentLinkedQueue<>();
        this.queue = new LinkedBlockingDeque<>();
    }

    public void send(Payload payload) {
        this.registerMethods.add(payload.getMethod());
        this.queue.add(payload.getPayload());
    }

    public String receive() {
        return this.queue.poll();
    }

    public void handleMethod() {
         this.registerMethods.poll();
    }
}
