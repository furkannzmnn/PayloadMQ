package org.example;

import java.time.LocalDateTime;
import java.util.Map;

public record Transaction<T>(T data, LocalDateTime transactionStartTime, Map<String, String> topicAndQueue,
                             LocalDateTime transactionEndTime) {
}
