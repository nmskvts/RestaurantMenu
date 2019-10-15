package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {

    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) { System.out.println(message); }

    public static String readString() throws IOException {
         return bufferedReader.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> dishesForOrder = new ArrayList<>();
        String dish;
        writeMessage("What would you like to order today : " + Dish.allDishesToString() + "?");
        while (!((dish = readString()).equals("exit"))) {
            try {
                dishesForOrder.add(Dish.valueOf(dish));
            } catch (IllegalArgumentException e) {
                writeMessage("Sorry, your choice is not presented at the menu.");
            }
        }
        return dishesForOrder;
    }
}
