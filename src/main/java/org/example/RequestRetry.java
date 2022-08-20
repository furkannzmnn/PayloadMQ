package org.example;

public final class RequestRetry implements Retry{
    // TODO: implement logger
    @Override
    public void retry(Runnable request, int retryCount, int maxRetryCount, long retryInterval) {
        if (retryCount < maxRetryCount) {
            try {
                Thread.sleep(retryInterval);
                request.run();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        } else {
            System.out.println("Max retry count reached");
        }
    }
}
