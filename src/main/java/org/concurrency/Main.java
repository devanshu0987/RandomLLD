package org.concurrency;

import java.util.PrimitiveIterator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        // DROP Initial Implementation: Guarded Block
//        Drop drop = new Drop();
//        (new Thread(new Producer(drop))).start();
//        (new Thread(new Consumer(drop))).start();

//        int taskThreashold = Runtime.getRuntime().availableProcessors();
//
//        var array = IntStream.rangeClosed(0, 1000).toArray();
//        ArraySumTask task = new ArraySumTask(array, 0, array.length - 1, taskThreashold);
//        Integer sum = ForkJoinPool.commonPool().invoke(task);
//        System.out.println(sum);

        SimpleThreadPool pool = new SimpleThreadPool(5);
        for (PrimitiveIterator.OfInt it = IntStream.rangeClosed(0, 100).iterator(); it.hasNext(); ) {
            var item = it.next();
            pool.execute(() -> {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(10000) % 1000);
                } catch (InterruptedException e) {
                    System.out.println("I have been interrupted");
                }
                // System.out.println(System.currentTimeMillis());
            });
        }
        while(pool.getPendingTaskCount() > 0) {
            System.out.println("Tasks still pending to execute. Sleeping " + pool.getPendingTaskCount());
            Thread.sleep(2000);
        }
        pool.shutdown();
    }
}
