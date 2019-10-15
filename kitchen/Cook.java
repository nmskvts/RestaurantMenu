package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Restaurant;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;

public class Cook extends Observable implements Runnable{

    private String name;
    private boolean busy;
    private LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>();

    public boolean isBusy() { return busy; }

    public Cook(String name) {
        this.name = name;
        setQueue(Restaurant.getOrderQueue());
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) { this.queue = queue; }

    @Override
    public String toString() {
        return name;
    }

    public void startCookingOrder(Order order) throws InterruptedException {
        busy = true;
        ConsoleHelper.writeMessage("Start cooking - " + order.toString() +
                ", cooking time " + order.getTotalCookingTime() + "min");
        Thread.sleep(10 * order.getTotalCookingTime());
        setChanged();
        notifyObservers(order);
        StatisticManager.getInstance().register(new CookedOrderEventDataRow(order.getTablet().getNumber() + "",
                name,order.getTotalCookingTime() * 60,order.getDishes()));
        busy = false;
    }

    @Override
    public void run() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                while(true) {
                    if (!queue.isEmpty()) {
                            if (!isBusy()) {
                                try {
                                    startCookingOrder(queue.take());
                                } catch (InterruptedException e) {}
                            } else {
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {}
                            }
                    } else {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {}
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }
}
