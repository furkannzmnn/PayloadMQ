package org.example;


import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class ScheduleMessageStore {
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public void scheduleStore() {
        executor.scheduleAtFixedRate(() -> MessageStoreExecution.messageDataStores.stream()
                .filter(DB -> DB.messageHandleTime().isBefore(LocalDateTime.now().minusSeconds(14)))
                .map(MessageStoreExecution.messageDataStores::remove)
                .forEach(DB -> System.out.println("Message Store deleted")), 0, 1, java.util.concurrent.TimeUnit.SECONDS);
    }
}
