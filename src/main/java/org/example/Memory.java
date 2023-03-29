package org.example;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Memory {


    private static Memory instance = null;
    private static final ConcurrentMap<String, Integer> archive = new ConcurrentHashMap<>();
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

    public void addToArchive(String key, int value) {
     archive.put(key, value);
    }

    public long getNumberOfPeople(String year) {
       if(archive.containsKey(year)) {
           return archive.get(year);
       }
       return -1;
    }


}
