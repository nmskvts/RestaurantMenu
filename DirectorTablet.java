package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.Advertisement;
import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.math.BigDecimal;
import java.util.*;

public class DirectorTablet {

    public void printAdvertisementProfit() {
        Map<String,Long> map = new TreeMap<>(Collections.reverseOrder());
        map.putAll(StatisticManager.getInstance().getAdvertisementProfit());
        double totalAmount = 0;
        for (Map.Entry<String,Long> entry : map.entrySet()) {
            ConsoleHelper.writeMessage(entry.getKey() + " - " + new BigDecimal(entry.getValue()).movePointLeft(2));
            totalAmount += (double) entry.getValue() / 100;
        }
        ConsoleHelper.writeMessage(String.format("Total - %.2f",totalAmount));
    }

    public void printCookWorkloading() {
        Map<String,Map<String,Integer>> map = new TreeMap<>(Collections.reverseOrder());
        map.putAll(StatisticManager.getInstance().getCookWorkLoading());

        for (Map.Entry<String,Map<String,Integer>> entry : map.entrySet()) {
            Map<String,Integer> value = new TreeMap<>();
            value.putAll(entry.getValue());
            ConsoleHelper.writeMessage(entry.getKey());

            for (Map.Entry<String,Integer> reentry : value.entrySet()) {
                double time = (double) reentry.getValue() / 60;
                if (time > 0) {
                    ConsoleHelper.writeMessage(reentry.getKey() + " - " + (int) Math.ceil(time) + " min");
                }
            }
        }
    }

    public void printActiveVideoSet() {
        List<Advertisement> activeVideoSet = StatisticAdvertisementManager.getInstance().getActiveVideoSet();

        Comparator compareByName = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Advertisement)o1).getName().compareToIgnoreCase(((Advertisement)o2).getName());
            }
        };
        Collections.sort(activeVideoSet,compareByName);

        if (!activeVideoSet.isEmpty()) {
            for (Advertisement ad : activeVideoSet) {
                ConsoleHelper.writeMessage(ad.getName() + " - " + ad.getHits());
            }
        }
    }

    public void printArchivedVideoSet() {
        List<Advertisement> archivedVideoSet = StatisticAdvertisementManager.getInstance().getArchivedVideoSet();

        Comparator compareByName = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Advertisement)o1).getName().compareToIgnoreCase(((Advertisement)o2).getName());
            }
        };
        Collections.sort(archivedVideoSet,compareByName);

        if (!archivedVideoSet.isEmpty()) {
            for (Advertisement ad : archivedVideoSet) {
                ConsoleHelper.writeMessage(ad.getName());
            }
        }
    }
}
