package org.example.consumer;

import org.example.MessageStoreExecution;
import org.example.broker.BrokerCluster;
import org.example.data.Payload;

import java.util.concurrent.CompletableFuture;

public class Listener extends BrokerCluster{

    private final MessageStoreExecution dataStore = new MessageStoreExecution();
    public void listen() {
        while (true) {
            String message = receive();
            for (String method : registerMethods) {
                // invoke method
                Class<?> clazz = null;
                try {
                    clazz = Class.forName(method);
                    clazz.newInstance();
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    System.out.println("errror");
                }

                // execute class extented method
                try {
                    clazz.getMethod("ok", String.class).invoke(clazz.newInstance(), message);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                handleMethod();
                CompletableFuture.runAsync(() -> dataStore.storeData(new Payload(message, "topic", "method")))
                        .whenCompleteAsync((v, e) -> {
                            if (e != null) {
                                System.out.println(e.getMessage());
                            }
                        });
            }
        }
}
}
