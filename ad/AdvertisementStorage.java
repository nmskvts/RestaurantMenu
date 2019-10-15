package com.javarush.task.task27.task2712.ad;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementStorage {

    private final static AdvertisementStorage ADVERTISEMENT_STORAGE = new AdvertisementStorage();
    private final List<Advertisement> VIDEOS = new ArrayList<>();

    private AdvertisementStorage() {
        Object someContent = new Object();
        add(new Advertisement(someContent, "First Video", 5000, 100, 3 * 60)); // 3 min
        add(new Advertisement(someContent, "Second Video", 100, 10, 15 * 60)); //15 min
        add(new Advertisement(someContent, "Third Video", 400, 0, 10 * 60)); //10 min
        add(new Advertisement(someContent, "Fourth Video", 800, 10, 5 * 60));
        add(new Advertisement(someContent, "Пятое видео", 2500, 5, 13 * 60));
    }

    public static AdvertisementStorage getInstance() { return ADVERTISEMENT_STORAGE; }

    public List<Advertisement> list() { return VIDEOS; }

    public void add(Advertisement advertisement) { VIDEOS.add(advertisement); }

}
