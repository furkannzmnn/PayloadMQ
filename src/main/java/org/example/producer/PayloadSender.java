package org.example.producer;

import org.example.JsonUtil;
import org.example.Loggers;
import org.example.Transaction;
import org.example.TransactionSync;
import org.example.data.Payload;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public final class PayloadSender {

    private final boolean transaction;
    static final File file = new File("/Users/furkanozmen/Desktop/tapucom/PayloadMQ/src/main/resources/properties.yml");

    {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            // ignore
        }
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(inputStream);
        Map<String, Object> var20 = (Map<String, Object>) data.get("kafka");
        List<String> var40  = (List<String>) var20.get("transaction");
        transaction = Boolean.parseBoolean(var40.iterator().next());
        Loggers.trace(() -> "yml resolved");
    }

    public CompletableFuture<Void> sendAsync(String topic, String message) {
        Loggers.trace(() -> ("-------------------------------------------------------"));
        Loggers.trace(() -> "START API CALL");
        final Payload meta = new Payload(message, topic);
        if (!JsonUtil.isValidJson(meta.getPayload())) {
            new CompletableFuture<>().completeExceptionally(new RuntimeException("invalid json"));
        }

        if (transaction) {
            return doSendTransaction(meta);
        }
        return doSend(meta);
    }

    public CompletableFuture<Void> doSendTransaction(Payload meta) {
        Transaction<Payload> transaction = new Transaction<>(
                meta, LocalDateTime.now(), Map.of("topic", meta.getTopic()), (LocalDateTime) null
        );
        new TransactionSync<Payload>().send(() -> send(meta), transaction);
        return new CompletableFuture<>();
    }

    public CompletableFuture<Void> doSend(Payload meta) {
        return CompletableFuture.runAsync(() -> send(meta));
    }

    private void send(Payload meta) {
        MessageExecution.send(meta);
        Loggers.trace(() -> "END API CALL");
    }


}
