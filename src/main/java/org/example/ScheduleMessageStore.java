package org.example;



import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class ScheduleMessageStore {
    private transient int retryCount;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    public void scheduleStore() {
        try {
            executor.scheduleAtFixedRate(() -> MessageStoreExecution.messageDataStores.stream()
                    .filter(db -> db.messageHandleTime().isBefore(LocalDateTime.now().minusSeconds(1)))
                    .map(MessageStoreExecution.messageDataStores::remove)
                    .forEach(db -> System.out.println("Message Store deleted")), 0, 1, java.util.concurrent.TimeUnit.SECONDS);
        }catch (Exception e) {
            retryCount++;
            System.out.println("Exception in scheduleStore");
            RetryAdapter.retry(this::scheduleStore, retryCount, 3, 1);
        }
    }


}
