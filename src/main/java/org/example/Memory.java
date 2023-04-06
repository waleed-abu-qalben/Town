package org.example;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Memory {


    /*
    in this map we store groups of people
    each group represent the number of alive people in a particular year
    the key is a year, and the value is the number of alive  people in that year
    */
    private static final ConcurrentMap<String, Integer> archive = new ConcurrentHashMap<>();
    private static Memory instance = null;
    public static final int MAX_NUMBER_OF_NEWBORNS = 1000000;
    public static final int MAX_NUMBER_OF_Person_Age = 100;
    public static final int INITIAL_YEAR = 1900;
    private static int currentYear = INITIAL_YEAR;

    private Memory() {
    }

    public static synchronized Memory getInstance(){
        if(instance == null) {
            instance = new Memory();
        }
        return instance;
    }

    public  ConcurrentMap<String, Integer> getArchive() {
        return archive;
    }

    public int getCurrentYear() {
        return currentYear;
    }
    public void updateCurrentYear(){
        currentYear++;
    }


    public void addAlivePeopleToArchive(String currentYear, int numberOfPeople) {
     archive.put(currentYear, numberOfPeople);
    }

    public long getNumberOfPeople(String year) {
       if(archive.containsKey(year)) {
           return archive.get(year);
       }
       return -1;
    }


}
