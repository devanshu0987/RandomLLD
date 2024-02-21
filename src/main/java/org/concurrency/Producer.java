package org.concurrency;

import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;

    public Producer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        Random random = new Random();

        for (int i = 0;
             i < 500;
             i++) {
            drop.put(String.valueOf(i));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
        drop.put("DONE");
    }
}
