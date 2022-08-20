package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
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
        String basicJson = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";
        Payload payload = new Payload(basicJson, "ops", new Main().getMethod());

        CompletableFuture.runAsync(() -> new ScheduleMessageStore().scheduleStore());
        execute(payload);
    }

    private static void execute(Payload payload) {
        PayloadSender sender = new PayloadSender();
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

    public void ok(String payload) {
        try {
            final Payload payload1 = JsonMapper.getInstance().readValue(payload, Payload.class);
            System.out.println(payload1.getPayload());
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }
}