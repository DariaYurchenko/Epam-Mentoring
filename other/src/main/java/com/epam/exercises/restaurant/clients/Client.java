package com.epam.exercises.restaurant.clients;

public class Client {
    private double happiness;

    public Client(double happiness) {
        this.happiness = happiness;
    }

    public double getHappiness() {
        return happiness;
    }

    public void updateHappiness(double happiness) {
        System.out.println("Client's starting happiness is " +  String.format("%.2f", this.happiness));
        this.happiness = happiness;
        showHappiness();
    }

    private void showHappiness() {
        System.out.println("Now client's happiness is " + String.format("%.2f", happiness));
    }
}
