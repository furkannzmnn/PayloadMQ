package org.example;

@FunctionalInterface
public interface Retry {
    void retry(Runnable request, int retryCount, int maxRetryCount, long retryInterval);
}
