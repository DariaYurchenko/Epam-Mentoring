package com.epam.exercises.restaurant.products.extras;

import com.epam.exercises.restaurant.products.Product;

public class Ketchup extends Extra {

    public Ketchup(Product product) {
        super(product);
        this.description = "Ketchup";
    }

    @Override
    public String getDescription() {
        return product.getDescription() + ", ketchup";
    }

    @Override
    public double changeHappiness(double happiness) {
        return product.changeHappiness(happiness) * 2;
    }
}
