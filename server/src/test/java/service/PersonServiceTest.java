package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.Database;
import model.Person;
import request.PersonRequestAll;
import request.PersonRequestOne;
import request.RegisterRequest;
import result.PersonResultAll;
import result.PersonResultOne;
import result.RegisterResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class PersonServiceTest {
    Database db;
    PersonRequestOne goodRequestOne;
    PersonRequestOne badRequestOne;
    PersonRequestAll goodRequestAll;
    PersonRequestAll badRequestAll;

    @Before
    public void setUp() throws Exception {
        // create database and sample request
        db = new Database();
        db.createTables();
        db.clearTables();
        db.openConnection();

        // register a user in order to test
        RegisterRequest newUser = new RegisterRequest("MyNewUserName", "1234321", "_email", "_first", "_last", "m");
        RegisterService service = new RegisterService();
        RegisterResult regResult = service.register(newUser);

        // create requests
        goodRequestOne = new PersonRequestOne(regResult.getAuthToken(), regResult.getPersonID());
        badRequestOne = new PersonRequestOne(regResult.getAuthToken(), "badPersonId");
        goodRequestAll = new PersonRequestAll(regResult.getAuthToken());
        badRequestAll = new PersonRequestAll("badRequest");
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);
    }

    // Get one person from the database
    @Test
    public void personOnePass() throws Exception {
        // create the service we are testing
        PersonService service = new PersonService();
        PersonResultOne result = service.onePerson(goodRequestOne);

        // compare values
        assertNull(result.getErrorMessage());
        assertEquals(result.getDescendant(), "MyNewUserName");
        assertEquals(result.getPersonID(), goodRequestOne.getPersonID());
        assertEquals(result.getFirstName(), "_first");
        assertEquals(result.getLastName(), "_last");
        assertEquals(result.getGender(), "m");
        assertNull(result.getSpouse());
        assertNotNull(result.getFather());
        assertNotNull(result.getMother());
    }

    // This should fail because the personID is not in the database
    @Test
    public void personOneFail() throws Exception {
        // create the service we are testing
        PersonService service = new PersonService();
        PersonResultOne result = service.onePerson(badRequestOne);

        // compare values
        assertNotNull(result.getErrorMessage());
        assertNull(result.getDescendant());
        assertNull(result.getPersonID());
        assertNull(result.getFirstName());
        assertNull(result.getLastName());
        assertNull(result.getGender());
        assertNull(result.getSpouse());
        assertNull(result.getFather());
        assertNull(result.getMother());
    }

    // Get one person from the database
    @Test
    public void personAllPass() throws Exception {
        // create the service we are testing
        PersonService service = new PersonService();
        PersonResultAll result = service.allPersons(goodRequestAll);

        for (Person person : result.getData()) {
            // compare values
            assertEquals(person.getDescendant(), "MyNewUserName");
            assertNotNull(person.getPersonID());
            assertNotNull(person.getFirstName());
            assertNotNull(person.getLastName());
            assertNotNull(person.getGender());
            assertNotNull(person.getFather());
            assertNotNull(person.getMother());
        }
        assertNull(result.getErrorMessage());
    }

    // This should fail because the authToken is not in the database
    @Test
    public void personAllFail() throws Exception {
        // create the service we are testing
        PersonService service = new PersonService();
        PersonResultAll result = service.allPersons(badRequestAll);

        // compare values
        assertNotNull(result.getErrorMessage());
        assertNull(result.getData());
    }
}
