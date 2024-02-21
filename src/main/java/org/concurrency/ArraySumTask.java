package org.concurrency;

import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class ArraySumTask extends RecursiveTask<Integer> {
    private final int[] array;
    private final int startInclusive;
    private final int endInclusive;
    private int taskThreshold = 22;

    public ArraySumTask(int[] array, int startInclusive, int endInclusive, int taskThreshold) {
        this.array = array;
        this.startInclusive = startInclusive;
        this.endInclusive = endInclusive;
        this.taskThreshold = taskThreshold;
    }

    @Override
    protected Integer compute() {
        if ((endInclusive - startInclusive) < taskThreshold) {
            System.out.println("SUM RANGE : " + startInclusive + " " + endInclusive);
            return IntStream.rangeClosed(startInclusive, endInclusive).map(i -> array[i]).sum();
        }

        int mid = startInclusive + (endInclusive - startInclusive) / 2;
        ArraySumTask leftSum = new ArraySumTask(array, startInclusive, mid, taskThreshold);
        ArraySumTask rightSum = new ArraySumTask(array, mid + 1, endInclusive, taskThreshold);
        int left = leftSum.compute();
        rightSum.fork();
        return left + rightSum.join();
    }
}
