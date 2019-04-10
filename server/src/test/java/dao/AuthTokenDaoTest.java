package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import model.AuthToken;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class AuthTokenDaoTest {
    Database db;
    AuthToken token1;
    AuthToken token2;

    @Before
    public void setUp() throws Exception {
        // create a new database
        db = new Database();

        // create new tokens
        token1 = new AuthToken("natedog", "123456789");
        token2 = new AuthToken("nmcla277", "44556677");
        db.createTables();
    }

    @After
    public void tearDown() throws Exception {
        db.clearTables();
    }

    @Test
    public void insertPass() throws Exception {
        AuthToken compareTest = null;
        // clear the database
        db.clearTables();
        try {
            // get connection and make new DAO
            Connection conn = db.openConnection();
            AuthTokenDao dao = new AuthTokenDao(conn);
            dao.insert(token1);
            compareTest = dao.query(token1.getAuthToken());
            // close db
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNotNull(compareTest);
        assertEquals(token1, compareTest);
    }

    @Test
    public void insertFail() throws Exception {
        // try to make it fail
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            AuthTokenDao dao = new AuthTokenDao(conn);
            dao.insert(token1);
            dao.insert(token1);
            db.closeConnection(didItWork);
        } catch (DataAccessException e) {
            //If we catch an exception we will end up in here
            db.closeConnection(false);
            didItWork = false;
        }
        //Check to make sure that we did in fact enter our catch statement
        assertFalse(didItWork);

        AuthToken compareTest = token1;
        try {
            Connection conn = db.openConnection();
            AuthTokenDao dao = new AuthTokenDao(conn);
            compareTest = dao.query(token1.getAuthToken());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //Now make sure that compareTest is indeed null
        assertNull(compareTest);

    }

    @Test
    public void queryPass() throws Exception {
        AuthToken compareTest = null;
        db.clearTables();

        try {
            Connection conn = db.openConnection();
            AuthTokenDao dao = new AuthTokenDao(conn);
            dao.insert(token1);
            dao.insert(token2);
            compareTest = dao.query(token2.getAuthToken());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        // make sure compareTest isn't null
        assertNotNull(compareTest);
        assertEquals(token2, compareTest);
    }

    @Test
    public void queryFail() throws Exception {
        db.clearTables();

        try {
            Connection conn = db.openConnection();
            AuthTokenDao dao = new AuthTokenDao(conn);
            dao.insert(token1);
            dao.insert(token2);
            assertNull(dao.query("wrong_ID"));
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
    }

    @Test
    public void deletePass() throws Exception {
        AuthToken compareTest = null;
        db.clearTables();

        try {
            Connection conn = db.openConnection();
            AuthTokenDao dao = new AuthTokenDao(conn);
            dao.insert(token1);
            dao.insert(token2);

            dao.delete();

            compareTest = dao.query(token2.getAuthToken());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        // make sure compareTest is null
        assertNull(compareTest);
    }

}
