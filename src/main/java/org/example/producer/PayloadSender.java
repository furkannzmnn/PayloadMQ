package org.example.producer;

import org.example.JsonUtil;
import org.example.data.Payload;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public final class PayloadSender {
    private static final Logger LOGGER = Logger.getLogger(PayloadSender.class.getName());

    public CompletableFuture<Void> sendAsync(String topic, String message) {
        LOGGER.info("-------------------------------------------------------");
        LOGGER.info("START API CALL");
        final Payload meta = new Payload(message, topic);
        if (! JsonUtil.isValidJson(meta.getPayload())) {
             new CompletableFuture<>().completeExceptionally(new RuntimeException("invalid json"));
        }

        return CompletableFuture.runAsync(() -> send(meta));
    }


    private void send(Payload meta) {
        MessageExecution.send(meta);
        LOGGER.info("END API CALL");
    }



}
