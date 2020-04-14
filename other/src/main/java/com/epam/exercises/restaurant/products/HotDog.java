package com.epam.exercises.restaurant.products;

public class HotDog extends Product {

    public HotDog() {
        this.description = "Hot dog";
    }

    @Override
    public double changeHappiness(double happiness) {
        return happiness * 1.05;
    }
}
