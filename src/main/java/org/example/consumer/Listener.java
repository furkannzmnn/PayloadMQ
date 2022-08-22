package org.example.consumer;

import org.example.ListenerInvoker;
import org.example.Loggers;
import org.example.MessageStoreExecution;
import org.example.broker.BrokerCluster;
import org.example.data.Payload;

import java.util.concurrent.CompletableFuture;

public class Listener extends BrokerCluster {
    private final MessageStoreExecution dataStore = new MessageStoreExecution();

    public void listen() {
        for (;;)
            while (this.topicQueues.size() > 0) {
                String message = receive();
                completeEvent(message);
            }
    }

    private void completeEvent(String message) {
        if (message != null) {
            ListenerInvoker.invoke(message);
            CompletableFuture.runAsync(() -> dataStore.storeData(new Payload(message, "topic")))
                    .whenCompleteAsync((v, e) -> {
                        if (e != null) {
                            Loggers.trace( () -> ( this.getClass().getName() + "----" + e.getMessage()));
                        }
                    });
            Loggers.trace( () -> (message + ",Message received: {}"));
        }
    }
}
