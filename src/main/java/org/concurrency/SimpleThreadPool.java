package org.concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class SimpleThreadPool {
    private BlockingQueue<Runnable> tasksQueue;
    private Thread[] workers;
    private volatile boolean isShutdownRequested;

    public SimpleThreadPool(int corePoolSize) {
        // We can accept all tasks
        this.isShutdownRequested = false;
        this.tasksQueue = new LinkedBlockingQueue<>();
        this.workers = new Worker[corePoolSize];
        for(int i = 0; i < corePoolSize; i++) {
            workers[i] = new Worker();
            workers[i].start();
        }
    }

    public void execute(Runnable task) throws InterruptedException {
        tasksQueue.put(task);
    }

    public void shutdown() {
        System.out.println("Initiating Shutdown");
        this.isShutdownRequested = true;
        for(int i = 0; i < workers.length; i++) {
            workers[i].interrupt();
        }
    }

    public int getPendingTaskCount() {
        return tasksQueue.size();
    }

    private class Worker extends Thread {
        public Worker() {
        }

        @Override
        public void run() {
            System.out.println("Worker start : " + getId() + " " + System.currentTimeMillis());
            try {
                while (!isShutdownRequested) {
                    // Take is a blocking call. It will wait until we get a task to run.
                    Runnable task = tasksQueue.take();
                    task.run();
                }
            } catch (InterruptedException ex) {
                System.out.println(getId() + " Interrupted : " + ex);
            }
            System.out.println("Worker end : " + getId() + " " + System.currentTimeMillis());
        }
    }
}
