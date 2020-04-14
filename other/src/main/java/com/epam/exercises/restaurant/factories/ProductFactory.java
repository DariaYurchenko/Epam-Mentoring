package com.epam.exercises.restaurant.factories;

import com.epam.exercises.restaurant.products.Chips;
import com.epam.exercises.restaurant.products.HotDog;
import com.epam.exercises.restaurant.products.Product;

public class ProductFactory {

    public Product createProduct(String item) {
        switch(item.toLowerCase()) {
            case "hot dog":
                return new HotDog();
            case "chips":
                return new Chips();
            default:
                throw new IllegalArgumentException("There is no such product in menu.");
        }
    }
}
