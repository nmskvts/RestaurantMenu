package com.javarush.task.task27.task2712.ad;

import java.util.ArrayList;
import java.util.List;

public class StatisticAdvertisementManager {

    private final static StatisticAdvertisementManager STATISTIC_ADVERTISEMENT_MANAGER = new StatisticAdvertisementManager();
    private final AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager() {}

    public static StatisticAdvertisementManager getInstance() { return STATISTIC_ADVERTISEMENT_MANAGER; }

    public List<Advertisement> getActiveVideoSet() {
        List<Advertisement> activeVideoSet = new ArrayList<>();
        for (Advertisement ad : advertisementStorage.list()) {
            if (ad.getHits() > 0) {
                activeVideoSet.add(ad);
            }
        }
        return activeVideoSet;
    }

    public List<Advertisement> getArchivedVideoSet() {
        List<Advertisement> archivedVideoSet = new ArrayList<>();
        for (Advertisement ad : advertisementStorage.list()) {
            if (ad.getHits() == 0) {
                archivedVideoSet.add(ad);
            }
        }
        return archivedVideoSet;
    }


}
