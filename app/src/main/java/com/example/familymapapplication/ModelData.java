package com.example.familymapapplication;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

import model.Person;
import model.Event;

public class ModelData {
    private static ModelData singleton = new ModelData( );

    private String serverPort;
    private String serverHost;
    private String authToken;
    private String personID; // -
    private String userName;
    private String errorMessage;

    private HashMap<String, Person> persons;
    private HashMap<String, Event> events;


    private HashMap<String, List<Event>> personEvents;
    //private Settings settings;
    //private Filter filter;
    private List<String> eventTypes;
    private HashMap<String, Float> eventTypeColors;
    private Person user;
    private HashSet<String> paternalAncestors;
    private HashSet<String> maternalAncestors;
    private HashMap<String, List<Person>> personChildren;


    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private ModelData() { }

    /* Static 'instance' method */
    public static ModelData getInstance( ) {
        return singleton;
    }


    public ArrayList<Person> getImmediateFamily(String personID) {
        ArrayList<Person> returnArray = new ArrayList<>();

        Person person = persons.get(personID);
        Person father = persons.get(person.getFather());
        Person mother = persons.get(person.getMother());
        Person spouse = persons.get(person.getSpouse());

        if (father != null) returnArray.add(father);
        if (mother != null) returnArray.add(mother);
        if (spouse != null) returnArray.add(spouse);



//        if (person.getGender().equalsIgnoreCase("m")) {
        for (Person possibleChild : persons.values()) {
            if (possibleChild.getFather().equalsIgnoreCase(personID) || possibleChild.getMother().equalsIgnoreCase(personID)) {
                returnArray.add(possibleChild);
            }
        }
//        }
//        else {
//            for (Person possibleChild : persons.values()) {
//                if (possibleChild.getMother().equalsIgnoreCase(personID)) {
//                    returnArray.add(possibleChild);
//                }
//            }
//        }

        return returnArray;
    }

    public ArrayList<Event> getLifeEvents(String personID) {
        Comparator compare = new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.toLowerCase().compareTo(o2.toLowerCase());
            }
        };

        TreeMap<String, Event> birthEvents = new TreeMap<>(compare);
        TreeMap<String, Event> regularEvents = new TreeMap<>(compare);
        TreeMap<String, Event> deathEvents = new TreeMap<>(compare);

        for (Event event : events.values()) {
            if (event.getPersonID().equalsIgnoreCase(personID)) {
                String key = event.getEventType().toLowerCase() + event.getYear();
                if (event.getEventType().equalsIgnoreCase("birth")) {
                    birthEvents.put(key, event);
                }
                else if (event.getEventType().equalsIgnoreCase("death")) {
                    deathEvents.put(key, event);
                }
                else {
                    regularEvents.put(key, event);
                }
            }
        }
        ArrayList<Event> returnList = new ArrayList<>();

        for (Event event : birthEvents.values()) {
            returnList.add(event);
        }
        for (Event event : regularEvents.values()) {
            returnList.add(event);
        }
        for (Event event : deathEvents.values()) {
            returnList.add(event);
        }

        return returnList;
    }


//    private void setRelatives(Person person) {
//        person
//    }



//    /* Other methods protected by singleton-ness */
//    public Person getUserInfo(String personID) {   // FIXME: Can this be?
//        System.out.println("In getUserInfo");
//        for (Person person : persons) {
//            if (person.getPersonID().equals(personID)) {
//                return person;
//            }
//        }
//        return null;
//    }


    // Getters and Setters
    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public static ModelData getSingleton() {
        return singleton;
    }

    public static void setSingleton(ModelData singleton) {
        ModelData.singleton = singleton;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HashMap<String, Person> getPersons() {
        return persons;
    }

    public void setPersons(HashMap<String, Person> persons) {
        this.persons = persons;

        System.out.println("persons length!!! " + persons.size());
    }

    public HashMap<String, Event> getEvents() {
        return events;
    }

    public void setEvents(HashMap<String, Event> events) {
        this.events = events;

        System.out.println("events length!!! " + events.size());
    }

    public Event getSpecificEvent(String eventID) {
        return events.get(eventID);
    }

    public Person getSpecificPerson(String personID) {
        return persons.get(personID);
    }
}