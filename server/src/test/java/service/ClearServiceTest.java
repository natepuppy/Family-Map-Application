package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.Database;
import result.ClearResult;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ClearServiceTest {
    Database db;

    @Before
    public void setUp() throws Exception {
        // create database
        db = new Database();
        db.createTables();
        db.openConnection();
    }

    @After
    public void tearDown() throws Exception {
        db.closeConnection(true);
    }

    // Insert the new user into the database
    @Test
    public void clearPass() throws Exception {
        // create the service we are testing
        ClearService service = new ClearService();
        ClearResult result = service.clear();

        // compare values
        assertNull(result.getErrorMessage());
        assertNotNull(result.getMessage());
    }
}
