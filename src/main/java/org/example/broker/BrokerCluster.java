package org.example.broker;

import org.example.Loggers;
import org.example.data.Payload;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrokerCluster {

    private static final Logger LOGGER = Logger.getLogger(BrokerCluster.class.getName());
    protected Map<String, BlockingQueue<String>> topicQueues;


    public BrokerCluster() {
        this.topicQueues = new LinkedHashMap<>();
    }

    public void send(Payload payload) {
        if (topicQueues.containsKey(payload.getTopic())) {
            topicQueues.get(payload.getTopic()).add(payload.getPayload());
        } else {
            LinkedBlockingDeque<String> topicQueue = new LinkedBlockingDeque<>();
            topicQueue.add(payload.getPayload());
            topicQueues.put(payload.getTopic(),topicQueue);
        }
    }

    public String receive() {
        for (Map.Entry<String, BlockingQueue<String>> entry : topicQueues.entrySet()) {
            if (entry.getValue().size() > 0) {
                LOGGER.log(Level.INFO, "topic message reached:" +  entry.getKey());
                return entry.getValue().poll();
            }
        }
        return null;
    }

}
