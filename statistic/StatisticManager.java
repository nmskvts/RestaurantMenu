package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticManager {

    private final static StatisticManager STATISTIC_MANAGER = new StatisticManager();
    private final StatisticStorage statisticStorage = new StatisticStorage();

    private StatisticManager() {}

    public static StatisticManager getInstance() { return STATISTIC_MANAGER;}

    public void register(EventDataRow data) { statisticStorage.put(data); }

    public Map<String,Long> getAdvertisementProfit() {
        Map<String,Long> adProfit = new HashMap<>();
        List<EventDataRow> selectedVideos = statisticStorage.getStorage().get(EventType.SELECTED_VIDEOS);
        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);

        for (EventDataRow data : selectedVideos) {
            String date = formatter.format(data.getDate());
            if (adProfit.containsKey(date)) {
                adProfit.replace(date,adProfit.get(date) + ((VideoSelectedEventDataRow)data).getAmount());
            }
            else { adProfit.put(date,((VideoSelectedEventDataRow)data).getAmount()); }
        }
        return adProfit;
    }

    public Map<String,Map<String,Integer>> getCookWorkLoading() {
        Map<String,Map<String,Integer>> cookWork = new HashMap<>();
        List<EventDataRow> cookedOrders = statisticStorage.getStorage().get(EventType.COOKED_ORDER);
        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);

        for (EventDataRow data : cookedOrders) {
            String date = formatter.format(data.getDate());

            if (cookWork.containsKey(date)) {
                if (cookWork.get(date).containsKey(((CookedOrderEventDataRow)data).getCookName())) {
                    cookWork.get(date).replace(((CookedOrderEventDataRow)data).getCookName(),
                            cookWork.get(date).get(((CookedOrderEventDataRow)data).getCookName()) + data.getTime());
                }
                else {
                    cookWork.get(date).put(((CookedOrderEventDataRow)data).getCookName(),data.getTime());
                }
            }
            else {
                cookWork.put(date,new HashMap<>());
                cookWork.get(date).put(((CookedOrderEventDataRow)data).getCookName(),data.getTime());
            }
        }
        return cookWork;
    }


    private class StatisticStorage {

        private final Map<EventType, List<EventDataRow>> storage = new HashMap<>();

        private StatisticStorage() {
            storage.put(EventType.COOKED_ORDER,new ArrayList<>());
            storage.put(EventType.NO_AVAILABLE_VIDEO,new ArrayList<>());
            storage.put(EventType.SELECTED_VIDEOS, new ArrayList<>());
        }

        private void put(EventDataRow data) { storage.get(data.getType()).add(data); }

        private Map<EventType, List<EventDataRow>> getStorage() { return storage; }
    }
}
