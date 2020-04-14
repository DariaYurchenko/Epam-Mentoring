package com.epam.exercises.restaurant.robot;

import com.epam.exercises.restaurant.listeners.Order;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class DishRobotImpl implements DishRobot{
    private Queue<com.epam.exercises.restaurant.listeners.Order> orders = new LinkedList<>();

    @Override
    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public void cookDish()  {
        try {
            Thread.sleep(1000);
            while (!orders.isEmpty()) {
                System.out.println("Order is being cooked...");
                Thread.sleep(ThreadLocalRandom.current().nextInt(2000, 4000));
                Objects.requireNonNull(orders.peek()).onReady();
                orders.poll();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Failed to cook dish.");
        }
    }
}
