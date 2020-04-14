package com.epam.exercises.sportbetting;

import com.epam.exercises.sportbetting.broker.Broker;
import com.epam.exercises.sportbetting.service.SportEventService;
import com.epam.exercises.sportbetting.service.impl.SportEventServiceImpl;
import com.epam.exercises.sportbetting.ui.ConsoleUi;
import com.epam.exercises.sportbetting.utils.DataGenerator;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        SportEventService sportEventService = new SportEventServiceImpl();
        Broker broker = new Broker(sportEventService, DataGenerator.generateData());
        ConsoleUi consoleUi = new ConsoleUi(broker);
        consoleUi.run();
    }

}
