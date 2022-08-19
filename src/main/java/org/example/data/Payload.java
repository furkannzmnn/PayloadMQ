package org.example.data;

import java.util.UUID;
import java.util.concurrent.Future;

public class Payload {
    private final UUID id = UUID.randomUUID();
    private String payload;
    private String topic;
    private String method;

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

    public String getMethod() {
        return method;
    }

    public Payload(String payload, String topic, String method) {
        this.payload = payload;
        this.method = method;
        this.topic = topic;
    }

    public Payload() {

    }
}
