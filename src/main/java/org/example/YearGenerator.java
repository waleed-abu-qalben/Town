package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.SecureRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


public class YearGenerator {
    private static final int NUMBER_OF_THREADS = 10;
    private static final Logger LOGGER = LogManager.getLogger(YearGenerator.class.getName());
    private final Memory memory = Memory.getInstance();
    private final Town town = Town.getInstance();
    private static final SecureRandom generator = new SecureRandom();
    public void generateNewYear() throws InterruptedException {
       generateNewborns();
       removeDeadPeople();
       archivePeopleAlive();
       updateCurrentYear();
    }

    /*
     this method is used to:
      - generate a random number of people
      - give each one of them a random age
      - calculate date of death, by adding their age to current year
      - store them as groups in the town's people map
      - the date of death as key and number of people will die in that year as value
      - this process done concurrently.

    */
    private void generateNewborns() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        int newborns = generator.nextInt(Memory.MAX_NUMBER_OF_NEWBORNS)+1;

        int currentYear = memory.getCurrentYear();


        for (int i = 0 ; i < newborns; i++) {
            int personAge =ThreadLocalRandom.current().nextInt(Memory.MAX_NUMBER_OF_Person_Age);
            int dateOfDeath = currentYear + personAge;
            executorService.submit(() -> town.insertPerson(dateOfDeath));

        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        LOGGER.info("Current Year : "+ memory.getCurrentYear());
        LOGGER.info("Original people :\n"+town.getPeople());
    }
    
    // this method is used to remove people will die in the current year
    private  void removeDeadPeople() {
       int currentYear =  memory.getCurrentYear();
      int numberOfDead =  town.removeYear(currentYear);
      LOGGER.info("number of Dead = "+numberOfDead);
      LOGGER.info("People after cleaning:\n"+town.getPeople());
    }

    /*
    this method responsible to call add to archive method from memory class
     and pass to it the current year and number of people alive.
    */
    private void archivePeopleAlive() {
        String currentYear = String.valueOf(memory.getCurrentYear());
        int numberOfPeopleAlive = getNumberOfPeopleAlive();
        memory.addAlivePeopleToArchive(currentYear, numberOfPeopleAlive);
        LOGGER.info("Archive \n"+ memory.getArchive());

    }
    private void updateCurrentYear() {
        memory.updateCurrentYear();
    }
    private int getNumberOfPeopleAlive(){
        return town.sumAllValues();
    }


}
