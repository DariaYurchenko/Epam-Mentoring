package com.epam.exercises.restaurant.robot;

import com.epam.exercises.restaurant.listeners.Order;

public interface DishRobot {

    void addOrder(Order order);

    void cookDish();
}
