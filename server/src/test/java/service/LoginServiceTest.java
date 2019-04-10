package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.Database;
import request.LoginRequest;
import request.RegisterRequest;
import result.LoginResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class LoginServiceTest {
    Database db;
    LoginRequest request;
    LoginRequest badRequest;

    @Before
    public void setUp() throws Exception {
        // create database and sample request
        db = new Database();
        db.createTables();
        db.clearTables();
        db.openConnection();

        request = new LoginRequest("myNewUserName", "1234321");
        badRequest = new LoginRequest("User_name_that_isnt_recognized", "new_password");

        // create a user to test with
        RegisterRequest newUser = new RegisterRequest("myNewUserName", "1234321", "_email", "_first", "_last", "m");
        RegisterService service = new RegisterService();
        service.register(newUser);
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);
    }

    // login a user who is already in the database
    @Test
    public void loginPass() throws Exception {
        // create the service we are testing
        LoginService service = new LoginService();
        LoginResult result = service.login(request);

        // compare values
        assertNull(result.getErrorMessage());
        assertEquals(result.getUserName(), "myNewUserName");
        assertNotNull(result.getPersonID());
        assertNotNull(result.getAuthToken());
    }

    // try to login a user that isn't in the db, it should return an error message
    @Test
    public void loginFail() throws Exception {
        // create the service we are testing
        LoginService service = new LoginService();
        LoginResult result = service.login(badRequest);

        // compare values
        assertNotNull(result.getErrorMessage());
        assertNull(result.getUserName());
        assertNull(result.getPersonID());
        assertNull(result.getAuthToken());
    }
}
