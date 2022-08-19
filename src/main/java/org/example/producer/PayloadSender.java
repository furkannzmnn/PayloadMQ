package org.example.producer;

import org.example.JsonUtil;
import org.example.data.Payload;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class PayloadSender {

    public CompletableFuture<Void> sendAsync(String topic, String message, String method) {
        final Payload meta = new Payload(message, topic, method);
        if (! JsonUtil.isValidJson(meta.getPayload())) {
             new CompletableFuture<>().join();
        }
        return CompletableFuture.runAsync(() -> MessageExecution.send(meta));
    }


}
