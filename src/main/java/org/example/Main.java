package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Main {
    private static boolean isGenerating = true;
    private static final YearGenerator yearGenerator  = new YearGenerator();
    private static final Memory memory = Memory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        generateNewYear();
        takeUserInputs();
    }
    private static void generateNewYear() {
        Thread generationThread = new Thread(() -> {
            while (isGenerating) {
                try {
                    yearGenerator.generateNewYear();
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        generationThread.start();
    }
    private static void takeUserInputs() {
        Thread userInputThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (isGenerating) {
                System.out.println("Enter exit to stop the system: ");
                if (scanner.hasNext()) {
                    String input = scanner.next();
                    if(!input.equals("exit")) {
                        LOGGER.info("number of people in: "+input+" = "+memory.getNumberOfPeople(input));
                        System.out.println(memory.getNumberOfPeople(input));

                    } else {
                        System.exit(0);
                        isGenerating = false;
                    }
                }
            }
        });
        userInputThread.start();
    }
}