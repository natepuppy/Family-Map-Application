package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.Database;
import model.Event;
import model.Person;
import model.User;
import request.EventRequestAll;
import request.EventRequestOne;
import request.LoadRequest;
import request.RegisterRequest;
import result.EventResultAll;
import result.EventResultOne;
import result.RegisterResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class EventServiceTest {
    Database db;
    EventRequestOne goodRequestOne;
    EventRequestOne badRequestOne;
    EventRequestAll goodRequestAll;
    EventRequestAll badRequestAll;

    @Before
    public void setUp() throws Exception {
        // create database and sample request
        db = new Database();
        db.createTables();
        db.clearTables();
        db.openConnection();

        createLoadRequest();

        // register a user in order to test
        RegisterRequest newUser = new RegisterRequest("nmcla274", "1234321", "_email", "_first", "_last", "m");
        RegisterService service = new RegisterService();
        RegisterResult regResult = service.register(newUser);

        // create requests
        goodRequestOne = new EventRequestOne(regResult.getAuthToken(), "Biking_123A");
        badRequestOne = new EventRequestOne(regResult.getAuthToken(), "badPersonId");
        goodRequestAll = new EventRequestAll(regResult.getAuthToken());
        badRequestAll = new EventRequestAll("badRequest");
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);
    }

    // Get one person from the database
    @Test
    public void eventOnePass() throws Exception {
        // create the service we are testing
        EventService service = new EventService();
        EventResultOne result = service.oneEvent(goodRequestOne);

        // compare values
        assertNull(result.getErrorMessage());
        assertEquals(result.getDescendant(), "nmcla274");
        assertEquals(result.getEventID(),"Biking_123A");
        assertEquals(result.getPersonID(),"12345");
        assertEquals(result.getCountry(),"Japan");
        assertEquals(result.getCity(),"Ushiku");
        assertEquals(result.getEventType(),"Biking_Around");
    }

    // Get one person from the database
    @Test
    public void eventAllPass() throws Exception {
        // create the service we are testing
        EventService service = new EventService();
        EventResultAll result = service.allEvents(goodRequestAll);

        for (Event event : result.getData()) {
            // compare values
            assertNull(result.getErrorMessage());
            assertEquals(event.getDescendant(), "nmcla274");
            assertNotNull(event.getEventID());
            assertNotNull(event.getPersonID());
            assertNotNull(event.getCountry());
            assertNotNull(event.getCity());
            assertNotNull(event.getEventType());
        }
    }

    // This should fail because the eventID is not in the database
    @Test
    public void eventOneFail() throws Exception {
        // create the service we are testing
        EventService service = new EventService();
        EventResultOne result = service.oneEvent(badRequestOne);

        // compare values
        assertNotNull(result.getErrorMessage());
        assertNull(result.getDescendant());
        assertNull(result.getEventID());
        assertNull(result.getPersonID());
        assertNull(result.getCountry());
        assertNull(result.getCity());
        assertNull(result.getEventType());
    }

    // This should fail because the authToken is not in the database
    @Test
    public void eventAllFail() throws Exception {
        // create the service we are testing
        EventService service = new EventService();
        EventResultAll result = service.allEvents(badRequestAll);

        // compare values
        assertNotNull(result.getErrorMessage());
        assertNull(result.getData());
    }

    // This function loads lot of data into the db
    private void createLoadRequest() {
        Event[] events = new Event[2];
        Person[] persons = new Person[2];
        User[] users = new User[2];

        // create entries for db
        Event event1 = new Event("Biking_123A", "nmcla274", "12345",
                10.3f, 10.3f, "Japan", "Ushiku",
                "Biking_Around", 2020);
        Event event2 = new Event("my_birth_1234", "nmcla274", "12345",
                1.3f, 60.45f, "US", "Phoenix",
                "birth", 1996);
        Person person1 = new Person("12345", "nmcla274", "nathan",
                "clark", "m", "mike", "michelle", "fingersCrossed");
        Person person2 = new Person("54321", "bilbo_baggins", "nathan",
                "clark", "m", "mike", "michelle", "fingersCrossed");
        User user1 = new User("blah", "password1", "nathanmclark23@gmail.com",
                "nathan", "clark", "m" , "1232341234");
        User user2 = new User("bilbo_baggins", "password1", "nathanmclark23@gmail.com",
                "nathan", "clark", "m" , "sadfsasd");

        events[0] = event1;
        events[1] = event2;
        persons[0] = person1;
        persons[1] = person2;
        users[0] = user1;
        users[1] = user2;

        LoadService service = new LoadService();
        try {
            service.load(new LoadRequest(users, persons, events));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
