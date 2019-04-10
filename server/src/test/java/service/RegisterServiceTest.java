package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.Database;
import request.RegisterRequest;
import result.RegisterResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class RegisterServiceTest {
    Database db;
    RegisterRequest request;

    @Before
    public void setUp() throws Exception {
        // create database and sample request
        db = new Database();
        request = new RegisterRequest("MyNewUserName", "_password", "_email", "_first", "_last", "m");
        db.createTables();
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);
    }

    // Insert the new user into the database
    @Test
    public void registerPass() throws Exception {
        // clear tables and open new connection
        db.clearTables();
        db.openConnection();

        // create the service we are testing
        RegisterService service = new RegisterService();
        RegisterResult result = service.register(request);

        // compare values
        assertNull(result.getErrorMessage());
        assertEquals(result.getUserName(), "MyNewUserName");
        assertNotNull(result.getPersonID());
        assertNotNull(result.getAuthToken());
    }

    // try to insert the same user, it should return an error message
    @Test
    public void registerFail() throws Exception {
        db.openConnection();

        // create the service we are testing
        RegisterService service = new RegisterService();
        RegisterResult result = service.register(request);

        // compare values
        assertNotNull(result.getErrorMessage());
        assertNull(result.getUserName());
        assertNull(result.getPersonID());
        assertNull(result.getAuthToken());
    }
}
