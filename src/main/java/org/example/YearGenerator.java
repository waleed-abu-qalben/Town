package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.SecureRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


public class YearGenerator {
    private static final Logger LOGGER = LogManager.getLogger(YearGenerator.class.getName());
    private final Memory memory = Memory.getInstance();
    private final Town town = Town.getInstance();
    private static final SecureRandom generator = new SecureRandom();
    public void generateNewYear() throws InterruptedException {
       generatePeople();
       removeDeadPeople();
       archivePeopleAlive();
       updateCurrentYear();

    }
    private void generatePeople () throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int newborns = //generator.nextInt(Memory.MAX_NUMBER_OF_NEWBORNS)+1;
                generator.nextInt(5)+1;

        int currentYear = memory.getCurrentYear();


        for (int i = 0 ; i < 10; i++) {
            int personAge = 19001;/*/*ThreadLocalRandom.current().nextInt(/*Memory.MAX_NUMBER_OF_Person_Age*/;
            int dateOfDeath = currentYear + personAge;
            executorService.submit(() -> town.insertPerson(dateOfDeath));

        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        LOGGER.info("Current Year : "+ memory.getCurrentYear());
        LOGGER.info("Original people :\n"+town.getPeople());
    }
    private  void removeDeadPeople() {
      int numberOfDead =  town.removeYear(memory.getCurrentYear());
      LOGGER.info("number of Dead = "+numberOfDead);
      LOGGER.info("People after cleaning:\n"+town.getPeople());
    }
    private void archivePeopleAlive() {
        memory.addToArchive(String.valueOf(memory.getCurrentYear()), town.getPeopleAlive());
        LOGGER.info("Archive \n"+ memory.getArchive());

    }
    private void updateCurrentYear() {
        memory.updateCurrentYear();
    }



}
