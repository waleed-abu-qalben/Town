package org.example;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Town {

    private static Town instance = null;
    private final ConcurrentMap<Integer, Integer> people;
    private Town(){
     people = new ConcurrentHashMap<>();
    }

    public static Town getInstance() {
        if(instance == null) {
            instance = new Town();
        }
        return instance;
    }

    public  ConcurrentMap<Integer, Integer> getPeople() {
        return people;
    }

    public  void insertPerson(int key) {
        people.compute(key, (k, v) -> (v == null) ? 1 : v + 1);
    }

    public  int removeYear(int year){
        if (people.containsKey(year)) {
            return people.remove(year);
        }
        return 0;
    }

    public int getPeopleAlive(){
        return people.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
