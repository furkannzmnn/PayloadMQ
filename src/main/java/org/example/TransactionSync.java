package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.broker.BrokerCluster;
import org.example.data.Payload;
import org.example.producer.MessageExecution;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class TransactionSync<T> {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    public void send(Runnable runnable, Transaction<T> transaction) {

        try {
            runnable.run();
        }catch (RuntimeException e) {
            BeginTransaction<T> tBeginTransaction = new BeginTransaction<>();
            final Transaction<T> beginTransaction = tBeginTransaction.beginTransaction(transaction);
            executorService.submit(() ->
                    new BeginTransactionStarter<>(executorService, tBeginTransaction).start((Payload) beginTransaction.data()));
        }
    }

    private record BeginTransactionStarter<T>(ExecutorService executorService, BeginTransaction<T> transaction) {
        public void start(Payload payload) {
                final BrokerCluster cluster = MessageExecution.BROKER_CLUSTER;
                final Map<String, BlockingQueue<String>> queueMap = cluster.getTopicQueues();
                if (queueMap.containsKey(payload.getTopic())) {
                    final BlockingQueue<String> blockingQueue = queueMap.get(payload.getTopic());
                    for (String s : blockingQueue) {
                        final ObjectMapper mapper = JsonMapper.getInstance();
                        try {
                            final Payload var1 = mapper.readValue(s, Payload.class);
                            if (var1.getId().equals(payload.getId())) {
                                blockingQueue.poll();
                            }
                        } catch (Exception e) {
                            Loggers.trace(() -> "transaction failed");
                            e.printStackTrace();
                        }

                    }
                }
            }


        }
}
