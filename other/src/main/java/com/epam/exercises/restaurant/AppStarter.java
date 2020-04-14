package com.epam.exercises.restaurant;

import com.epam.exercises.restaurant.clients.Client;
import com.epam.exercises.restaurant.factories.ProductFactory;
import com.epam.exercises.restaurant.listeners.Order;
import com.epam.exercises.restaurant.products.Product;
import com.epam.exercises.restaurant.products.extras.Ketchup;
import com.epam.exercises.restaurant.products.extras.Mustard;
import com.epam.exercises.restaurant.robot.DishRobot;
import com.epam.exercises.restaurant.robot.DishRobotImpl;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.UnaryOperator;

public class AppStarter {
    private static final List<String> DISHES = List.of("chips", "hot dog");
    private static final List<UnaryOperator<Product>> EXTRAS = List.of(Ketchup::new, Mustard::new);

    public static void main(String[] args) {
        DishRobot dishRobot = new DishRobotImpl();

        new Thread(dishRobot::cookDish).start();

        int ordersAmount = ThreadLocalRandom.current().nextInt(1, 5);
        for(int i = 1; i <= ordersAmount; i++) {
            dishRobot.addOrder(new Order(selectExtra(selectProduct()),
                    new Client(ThreadLocalRandom.current().nextDouble(1.0, 5.0))));
            System.out.println("Order " + i + " was taken...");
        }
    }

    private static Product selectProduct() {
        ProductFactory factory = new ProductFactory();
        return factory.createProduct(DISHES.get(ThreadLocalRandom.current().nextInt(DISHES.size())));
    }

    private static Product selectExtra(Product product) {
        int extraAmount = ThreadLocalRandom.current().nextInt(3);
        for(int i = 0; i < extraAmount; i++) {
            product = EXTRAS.get(ThreadLocalRandom.current().nextInt(EXTRAS.size())).apply(product);
        }
        return product;
    }
}
