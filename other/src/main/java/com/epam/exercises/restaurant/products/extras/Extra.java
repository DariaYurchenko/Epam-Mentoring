package com.epam.exercises.restaurant.products.extras;

import com.epam.exercises.restaurant.products.Product;

public abstract class Extra extends Product {
    Product product;

    public Extra(Product product) {
        this.product = product;
        this.description = "Unknown extra";
    }

    public abstract String getDescription();

}
