package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {

    private final static int ORDER_CREATING_INTERVAL = 100;
    private final static LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

    public static LinkedBlockingQueue<Order> getOrderQueue() { return orderQueue; }

    public static void main(String[] args) throws InterruptedException {
        Cook alex = new Cook("Alex");
        Cook james = new Cook("James");
        Waiter waiter = new Waiter();
        alex.addObserver(waiter);
        james.addObserver(waiter);
        Thread thread1 = new Thread(alex);
        Thread thread2 = new Thread(james);
        thread1.start();
        thread2.start();


        List<Tablet> tablets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Tablet t = new Tablet((int)(Math.random()*50));
            tablets.add(t);
        }

        Thread thread = new Thread(new RandomOrderGeneratorTask(tablets,ORDER_CREATING_INTERVAL));
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();

        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();

    }
}
