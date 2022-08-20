package org.example.data;

import java.util.UUID;
import java.util.concurrent.Future;

public class Payload {
    private final UUID id = UUID.randomUUID();
    private String payload;
    private String topic;

    public UUID getId() {
        return id;
    }

    public String getPayload() {
        return payload;
    }

    public String getTopic() {
        return topic;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Payload(String payload, String topic) {
        this.payload = payload;
        this.topic = topic;
    }

    public Payload() {

    }
}
