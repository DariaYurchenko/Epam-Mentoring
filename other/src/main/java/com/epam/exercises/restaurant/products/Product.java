package com.epam.exercises.restaurant.products;

public abstract class Product {
    protected String description = "Unknown product";

    public String getDescription() {
        return description;
    }

    public abstract double changeHappiness(double happiness);

}
