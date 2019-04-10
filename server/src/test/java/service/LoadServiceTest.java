package service;

import org.junit.Before;
import org.junit.Test;

import dao.Database;
import model.Event;
import model.Person;
import model.User;
import request.LoadRequest;
import result.LoadResult;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class LoadServiceTest {
    Database db = null;
    LoadRequest goodRequest = null;
    LoadRequest badRequest = null;

    @Before
    public void setUp() throws Exception {
        // create database and sample request
        db = new Database();
        goodRequest = createGoodLoadRequest();
        badRequest = createBadLoadRequest();
        db.createTables();
        db.openConnection();
    }

    // Insert the new user into the database
    @Test
    public void loadPass() throws Exception {
        // create the service we are testing
        LoadService service = new LoadService();
        LoadResult result = service.load(goodRequest);

        // compare values
        assertNull(result.getErrorMessage());
        assertNotNull(result.getMessage());
        db.closeConnection(true);
    }

    // try to run fill with a negative generations parameter, it should return an error message
    @Test
    public void loadFail() throws Exception {
        // create the service we are testing
        LoadService service = new LoadService();
        LoadResult result = service.load(badRequest);

        // compare values
        assertNotNull(result.getErrorMessage());
        assertNull(result.getMessage());
        db.closeConnection(true);
    }

    // This function creates data to load
    private LoadRequest createGoodLoadRequest() {
        Event[] events = new Event[2];
        Person[] persons = new Person[2];
        User[] users = new User[2];

        Event event1 = new Event("Biking_123A", "Gale", "12345",
                10.3f, 10.3f, "Japan", "Ushiku",
                "Biking_Around", 2020);
        Event event2 = new Event("my_birth_1234", "username", "12345",
                1.3f, 60.45f, "US", "Phoenix",
                "birth", 1996);
        Person person1 = new Person("12345", "nmcla274", "nathan",
                "clark", "m", "mike", "michelle", "fingersCrossed");
        Person person2 = new Person("54321", "bilbo_baggins", "nathan",
                "clark", "m", "mike", "michelle", "fingersCrossed");

        User user1 = new User("nmcla274", "password13", "thanmclark23@gmail.com",
                "nathan", "clark", "m" , "12345");
        User user2 = new User("bilbo_baggins", "password1", "nathanmclark23@gmail.com",
                "nathan", "clark", "m" , "54321");

        events[0] = event1;
        events[1] = event2;
        persons[0] = person1;
        persons[1] = person2;
        users[0] = user1;
        users[1] = user2;

        return new LoadRequest(users, persons, events);
    }

    // This function creates data to load
    private LoadRequest createBadLoadRequest() {
        // Notice that both events have the same eventID. This makes it a bad request

        Event[] events = new Event[2];
        Person[] persons = new Person[2];
        User[] users = new User[2];

        Event event1 = new Event("Biking_123A", "Gale", "12345",
                10.3f, 10.3f, "Japan", "Ushiku",
                "Biking_Around", 2020);
        Event event2 = new Event("Biking_123A", "username", "12345",
                1.3f, 60.45f, "US", "Phoenix",
                "birth", 1996);
        Person person1 = new Person("12345", "nmcla274", "nathan",
                "clark", "m", "mike", "michelle", "fingersCrossed");
        Person person2 = new Person("54321", "nmcla274", "nathan",
                "clark", "m", "mike", "michelle", "fingersCrossed");

        User user1 = new User("nmcla274", "password1", "nathanmclark23@gmail.com",
                "nathan", "clark", "m" , "12345");
        User user2 = new User("bilbo_baggins", "password1", "nathanmclark23@gmail.com",
                "nathan", "clark", "m" , "54321");

        events[0] = event1;
        events[1] = event2;
        persons[0] = person1;
        persons[1] = person2;
        users[0] = user1;
        users[1] = user2;

        return new LoadRequest(users, persons, events);
    }
}
