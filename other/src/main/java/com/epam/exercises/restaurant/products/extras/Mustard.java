package com.epam.exercises.restaurant.products.extras;

import com.epam.exercises.restaurant.products.Product;

public class Mustard extends Extra {

    public Mustard(Product product) {
        super(product);
        this.description = "Mustard";
    }

    @Override
    public String getDescription() {
        return product.getDescription() + ", mustard";
    }

    @Override
    public double changeHappiness(double happiness) {
        return product.changeHappiness(happiness) + 1 ;
    }
}
