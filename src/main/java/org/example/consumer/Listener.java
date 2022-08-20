package org.example.consumer;

import org.example.ListenerInvoker;
import org.example.MessageStoreExecution;
import org.example.broker.BrokerCluster;
import org.example.data.Payload;

import java.util.concurrent.CompletableFuture;

public class Listener extends BrokerCluster {

    private final MessageStoreExecution dataStore = new MessageStoreExecution();

    public void listen() {
        while (true) {
            while (this.queue.size() > 0) {
                String message = receive();
                // invoke method
                ListenerInvoker.invoke(message);
                CompletableFuture.runAsync(() -> dataStore.storeData(new Payload(message, "topic", "method")))
                        .whenCompleteAsync((v, e) -> {
                            if (e != null) {
                                System.out.println(e.getMessage());
                            }
                        });
                handleMethod();
            }
        }
    }
}
