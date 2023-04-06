package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Main {
    private static final boolean isGenerating = true;
    private static final YearGenerator yearGenerator  = new YearGenerator();
    private static final Memory memory = Memory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        generateNewYear();
        takeUserInputs();
    }

    // Thread1:
    // generates a new year avery 5 seconds (1 year)
    private static void generateNewYear() {
        Thread generationThread = new Thread(() -> {
            while (isGenerating) {
                try {
                    yearGenerator.generateNewYear();
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        generationThread.start();
    }


    /*
    * Thread2:
    * 1- takes a year as input from the user
    * 2- returns -1 if the year is not generated yet.
    * 3- returns the number of people alive in that year
    */
    private static void takeUserInputs() {
        Thread userInputThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (isGenerating) {
                System.out.println("Enter a year:\n");
                if (scanner.hasNext()) {
                    String input = scanner.next();
                        long numberOfPeople = memory.getNumberOfPeople(input);
                        if(numberOfPeople == -1) {
                           LOGGER.error("This year is not initialized yet");
                            System.out.println("This year is not initialized yet");
                        } else {
                            LOGGER.info("Number of people in: "+input+" = "+numberOfPeople);
                            System.out.println("Number of people in: "+input+" = "+numberOfPeople+"\n");
                        }

                }
            }
        });
        userInputThread.start();
    }
}