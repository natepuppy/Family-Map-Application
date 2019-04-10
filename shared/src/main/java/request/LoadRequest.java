package request;

import model.User;
import model.Person;
import model.Event;

/** The purpose of this class is to create a Java object that holds the request
 * information for the API call to /load */
public class LoadRequest {
    /** This variable is an Array of User objects */
    private User[] users;
    /** This variable is an Array of Person objects */
    private Person[] persons;
    /** This variable is an Array of Event objects */
    private Event[] events;


    /** Constructor that initializes all variables */
    public LoadRequest(User[] users, Person[] people, Event[] events) {
        this.users = users;
        this.persons = people;
        this.events = events;
    }

    /** Constructor that initializes everything to null */
    public LoadRequest() {
        this.users = null;
        this.persons = null;
        this.events = null;
    }


    /** convert all strings in the class to lower case */
    public void toLowerCase() {
        for (User user : users) {
            user.toLowerCase();
        }

        for (Person person : persons) {
            person.toLowerCase();
        }

        for (Event event : events) {
            event.toLowerCase();
        }
    }


    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}