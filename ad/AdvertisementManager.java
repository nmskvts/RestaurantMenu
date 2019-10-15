package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.*;
import java.util.stream.Collectors;

public class AdvertisementManager {

    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;

    public AdvertisementManager(int timeSeconds) { this.timeSeconds = timeSeconds; }

    public void processVideos() {
        if (storage.list().isEmpty()|| getFixedList().isEmpty()) { throw new NoVideoAvailableException(); }

        List<Advertisement> bestCombination = new ArrayList<>();
        getBestCombination(getFixedList(),bestCombination,timeSeconds);

        StatisticManager.getInstance().register(new VideoSelectedEventDataRow
                (bestCombination,getTotalAmount(bestCombination),getTotalDuration(bestCombination)));

        for (Advertisement ad : bestCombination) {
            ConsoleHelper.writeMessage(ad.toString());
            ad.revalidate();
        }
    }

    private List<Advertisement> getFixedList() {
        List<Advertisement> list = new ArrayList<>();
        for (Advertisement ad : storage.list()) {
            if (ad.getDuration() <= timeSeconds && ad.getHits() > 0) { list.add(ad); }
        }
        return list;
    }

    private void getBestCombination(List<Advertisement> start, List<Advertisement> finish, int timeLeft) {
        if (start.isEmpty()) {return;}

        Advertisement bestAdvertisement;

        Advertisement bestAmount = Collections.max(start, Comparator.comparing(Advertisement::getAmountPerOneDisplaying));
        List<Advertisement> bestAmounts = start.stream().
                filter(advertisement -> advertisement.getAmountPerOneDisplaying() == bestAmount.getAmountPerOneDisplaying()).
                collect(Collectors.toList());
        if (bestAmounts.size() > 1) {
            Advertisement bestDuration = Collections.max(bestAmounts,Comparator.comparing(Advertisement::getDuration));
            List<Advertisement> bestDurations = start.stream().
                    filter(advertisement -> advertisement.getDuration() == bestDuration.getDuration()).
                    collect(Collectors.toList());
            if (bestDurations.size() > 1) {
                bestAdvertisement = Collections.max(bestDurations,Comparator.comparing(Advertisement::getHits));
            }
            else { bestAdvertisement = bestDuration;}
        }
        else { bestAdvertisement = bestAmount;}

        start.remove(bestAdvertisement);
        if (timeLeft >= bestAdvertisement.getDuration()) {
            finish.add(bestAdvertisement);
            timeLeft -= bestAdvertisement.getDuration();
        }
        getBestCombination(start,finish,timeLeft);
    }

    private long getTotalAmount(List<Advertisement> list) {
        long result = 0;
        for (Advertisement ad : list) {
            result += ad.getAmountPerOneDisplaying();
        }
        return result;
    }

    private int getTotalDuration(List<Advertisement> list) {
        int result = 0;
        for (Advertisement ad : list) {
            result += ad.getDuration();
        }
        return result;
    }
}
