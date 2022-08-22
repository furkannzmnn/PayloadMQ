package org.example.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.JsonMapper;
import org.example.broker.BrokerCluster;
import org.example.consumer.Listener;
import org.example.data.Payload;

import java.util.concurrent.CompletableFuture;

public final class MessageExecution {
    private MessageExecution() {}

    public static final BrokerCluster BROKER_CLUSTER = new Listener();

    public static void send(Payload payload) {
             try {
                 payload.setPayload(JsonMapper.getInstance().writeValueAsString(payload));
             } catch (JsonProcessingException e) {
                 throw new RuntimeException(e);
             }
             BROKER_CLUSTER.send(payload);
         };
}
