package com.javarush.task.task27.task2712;

import java.util.ArrayList;
import java.util.List;

public class RandomOrderGeneratorTask implements Runnable {

    private List<Tablet> tablets ;
    private int interval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval) {
        this.tablets = tablets;
        this.interval = interval;
    }

    @Override
    public void run() {
        if (tablets.size() > 0) {
            while (!Thread.currentThread().isInterrupted()) {
                Tablet randomTablet = tablets.get((int) (Math.random() * tablets.size()));
                randomTablet.createTestOrder();
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) { return; }
            }
        }
    }
}
