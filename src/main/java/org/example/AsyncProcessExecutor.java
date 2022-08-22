package org.example;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AsyncProcessExecutor {

    public static CompletableFuture<Void> completeAsync(Runnable call) {
        return CompletableFuture.runAsync(call);
    }

    public static CompletableFuture<Void> completeAllAsync(CompletableFuture<?>[] calls) {
        return CompletableFuture.allOf(calls);
    }
}
