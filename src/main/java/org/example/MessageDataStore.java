package org.example;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author <a href="mailto:ozmenf97@gmail.com">Furkan Özmen</a>
 * @since 2022-08-01
 */
public record MessageDataStore(UUID id, LocalDateTime messageHandleTime, String message, String topic) {
}
