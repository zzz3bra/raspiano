package by.bru.raspiano.device.sensor;

public interface Task {
    /**
     * Start the task.
     */
    void start();

    /**
     * Check if the task is started.
     *
     * @return {@code true} if the task is started, otherwise
     * {@code false} is returned.
     */
    boolean isStarted();

    /**
     * Cancel the task.
     */
    void cancel();

    /**
     * Check if the task is canceled.
     *
     * @return {@code true} if the task is canceled, otherwise
     * {@code false} is returned.
     */
    boolean isCanceled();
}
