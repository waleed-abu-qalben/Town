package org.example;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Town {

    /*
    * we use this map to store the people alive currently in the Town
    * people stored in groups as key/value pairs
    * the key is the  year that the group of people will die at, in the future
    * the value is the number of people in that group
    * the map is concurrent to ensure thread safety.
    * I used map to remove the group of dead people faster O(1).
    */
    private final ConcurrentMap<Integer, Integer> people;
    private static Town instance = null;
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


    /*
    - this method used to add person to the map
    - the key is the year  of death
    - the value is 1, if this person is the first entry of his group
    - or value is number of people will die in that year + 1.
    */
    public  void insertPerson(int yearOfDeath) {
        people.compute(yearOfDeath, (k, v) -> (v == null) ? 1 : v + 1);
    }

    /*
    this method returns the number of people in the year passed to the parameters.
    or returns 0 if no one will die in that year.
    */
    public  int removeYear(int year){
        if (people.containsKey(year)) {
            return people.remove(year);
        }
        return 0;
    }
    /*
     this method sums all values in the map
     the sum of values is the same as number of people alive
    */
    public int sumAllValues(){
        return people.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
