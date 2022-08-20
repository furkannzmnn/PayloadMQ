package org.example;

public final class RetryAdapter {

    private RetryAdapter() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    private static final RequestRetry RETRY = new RequestRetry();
    public static void retry(Runnable request, int retryCount, int maxRetryCount, long retryInterval) {
        RETRY.retry(request, retryCount, maxRetryCount, retryInterval);
    }
}
