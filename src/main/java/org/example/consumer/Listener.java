package org.example.consumer;

import org.example.ListenerInvoker;
import org.example.Loggers;
import org.example.MessageStoreExecution;
import org.example.broker.BrokerCluster;
import org.example.data.Payload;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class Listener extends BrokerCluster {
    private final MessageStoreExecution dataStore = new MessageStoreExecution();

    public void listen() {
        while (true) {
            while (this.topicQueues.size() > 0) {
                String message = receive();
                // invoke method
                if (message != null) {
                    ListenerInvoker.invoke(message);
                    CompletableFuture.runAsync(() -> dataStore.storeData(new Payload(message, "topic")))
                            .whenCompleteAsync((v, e) -> {
                                if (e != null) {
                                    logger( this.getClass().getName(), e.getMessage());
                                }
                            });
                    logger(message,"Message received: {}");
                }
            }
        }
    }

    private void logger(String message, String text) {
        Loggers.log(Level.INFO, text, message);
    }
}
