package com.epam.exercises.restaurant.listeners;

import com.epam.exercises.restaurant.clients.Client;
import com.epam.exercises.restaurant.products.Product;

public class Order implements CookListener {
    private Client client;
    private Product product;

    public Order(Product product, Client client) {
        this.client = client;
        this.product = product;
    }

    @Override
    public void onReady() {
        System.out.println(product.getDescription() + " is ready.");
        client.updateHappiness(calculateHappiness());
    }

    private double calculateHappiness() {
        return product.changeHappiness(client.getHappiness());
    }

}
