package com.epam.exercises.restaurant.products;

public class Chips extends Product {

    public Chips() {
        this.description = "Chips";
    }

    @Override
    public double changeHappiness(double happiness) {
        return happiness + 2;
    }
}
