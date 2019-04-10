package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.Database;
import request.FillRequest;
import request.RegisterRequest;
import result.FillResult;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class FillServiceTest {
    Database db;
    FillRequest request;
    FillRequest badRequest;

    @Before
    public void setUp() throws Exception {
        // create database and sample request
        db = new Database();
        db.createTables();
        db.clearTables();
        db.openConnection();

        request = new FillRequest("MyNewUserName", 4);
        badRequest = new FillRequest("MyNewUserName", -1);

        // create a user to test with
        RegisterRequest newUser = new RegisterRequest("MyNewUserName", "1234321", "_email", "_first", "_last", "m");
        RegisterService service = new RegisterService();
        service.register(newUser);
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);
    }

    // Insert the new user into the database
    @Test
    public void fillPass() throws Exception {
        // create the service we are testing
        FillService service = new FillService();
        FillResult result = service.fill(request);

        // compare values
        assertNull(result.getErrorMessage());
        assertNotNull(result.getMessage());
    }

    // try to run fill with a negative generations parameter, it should return an error message
    @Test
    public void fillFail() throws Exception {
        // create the service we are testing
        FillService service = new FillService();
        FillResult result = service.fill(badRequest);

        // compare values
        assertNotNull(result.getErrorMessage());
        assertNull(result.getMessage());
    }
}
