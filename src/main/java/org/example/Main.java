package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.annotation.PayloadListener;
import org.example.consumer.Listener;
import org.example.data.Payload;
import org.example.producer.PayloadSender;

import java.util.concurrent.CompletableFuture;

import static org.example.producer.MessageExecution.BROKER_CLUSTER;

public class Main {

    private String getMethod() {
        return this.getClass().getName();
    }

    public static void main(String[] args) {
        PayloadSender sender = new PayloadSender();
        String basicJson = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";
        Payload payload = new Payload(basicJson, "ops", new Main().getMethod());

        CompletableFuture.runAsync(() -> new ScheduleMessageStore().scheduleStore());
        execute(payload, sender);
    }

    private static void execute(Payload payload, PayloadSender sender) {
        sender.sendAsync(payload.getTopic(), payload.getPayload(), payload.getMethod())
                .handle((result, ex) -> {
                    if (ex != null) {
                        System.out.println("an error occurred: "+ ex.getMessage());
                    }
                    return result;
                }).join();

        Listener ex = (Listener) BROKER_CLUSTER;
        ex.listen();
    }

    @PayloadListener
    public void initialize(String payload) {
        try {
            final Payload payload1 = JsonMapper.getInstance().readValue(payload, Payload.class);
            System.out.println("received: " + payload1.getPayload());
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }
}