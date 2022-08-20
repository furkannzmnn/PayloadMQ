package org.example;

import org.example.data.Payload;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;


public final class MessageStoreExecution {
    static final Deque<MessageDataStore> messageDataStores;

    static  {
        messageDataStores = new ArrayDeque<>();
    }

    public void storeData(Payload payload) {
        final String message = payload.getPayload();
        final String topic = payload.getTopic();
        final UUID id = payload.getId();
        final LocalDateTime messageHandleTime = LocalDateTime.now();

        MessageDataStore store = new MessageDataStore(id, messageHandleTime, message, topic);

        MessageStoreExecution.messageDataStores.add(store);

    }
}
