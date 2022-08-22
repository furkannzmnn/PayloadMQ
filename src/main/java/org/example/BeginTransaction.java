package org.example;

import org.example.data.Payload;

import java.util.LinkedHashMap;
import java.util.Map;

public final class BeginTransaction<T> {

    private static final String TRANSACTION_KEY = "transaction";

    private Transaction<T> tTransaction;

    // TODO: PERSİSTENCE CONTEXT ARAŞTIR

    private final Map<String, Transaction<T>> persistenceContext = new LinkedHashMap<>();

    public BeginTransaction(Transaction<T> tTransaction) {
        this.tTransaction = tTransaction;
    }

    public Transaction<?> beginTransaction() {
         persistenceContext.put(TRANSACTION_KEY, tTransaction);
         return persistenceContext.get(TRANSACTION_KEY);
    }
}
