package org.example;

import org.example.data.Payload;

import java.util.LinkedHashMap;
import java.util.Map;

public final class BeginTransaction<T> {

    private static final String TRANSACTION_KEY = "transaction";

    // TODO: PERSİSTENCE CONTEXT ARAŞTIR

    private final Map<String, Transaction<T>> persistenceContext = new LinkedHashMap<>();

    public Transaction<T> beginTransaction(Transaction<T> transaction) {
        return persistenceContext.put(TRANSACTION_KEY, transaction);
    }
}
