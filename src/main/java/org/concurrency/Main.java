package org.concurrency;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
//        // DROP Initial Implementation: Guarded Block
//        Drop drop = new Drop();
//        (new Thread(new Producer(drop))).start();
//        (new Thread(new Consumer(drop))).start();

        int taskThreashold = Runtime.getRuntime().availableProcessors();

        var array = IntStream.rangeClosed(0, 1000).toArray();
        ArraySumTask task = new ArraySumTask(array, 0, array.length - 1, taskThreashold);
        Integer sum = ForkJoinPool.commonPool().invoke(task);
        System.out.println(sum);
    }
}
