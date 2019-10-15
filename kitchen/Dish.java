package com.javarush.task.task27.task2712.kitchen;

public enum Dish {
    Fish(25),
    Steak(30),
    Soup(15),
    Juice(5),
    Water(3);

    private int duration;

    Dish(int duration) {this.duration = duration;}

    public int getDuration() {
        return duration;
    }

    public static String allDishesToString() {
        StringBuilder allDishes = new StringBuilder();
        for (Dish d : Dish.values()) {
            allDishes.append(d + ", ");
        }
        allDishes.delete(allDishes.length()-2,allDishes.length());
        return allDishes.toString();
    }
}
