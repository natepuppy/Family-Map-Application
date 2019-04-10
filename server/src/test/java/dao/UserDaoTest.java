package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UserDaoTest {
    Database db;
    User newUser;
    User newUser2;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        newUser = new User("nmcla274", "password1", "nathanmclark23@gmail.com", "nathan", "clark", "m" , "12345");
        newUser2 = new User("bilbo_baggins", "password1", "nathanmclark23@gmail.com", "nathan", "clark", "m" , "12345");
        db.createTables();
    }

    @After
    public void tearDown() throws Exception {
        db.clearTables();
    }

    // simply get user from the database
    @Test
    public void queryPass() throws Exception {
        User compareTest = null;
        db.clearTables();

        try {
            Connection conn = db.openConnection();
            UserDao userDao = new UserDao(conn);
            userDao.insert(newUser);
            userDao.insert(newUser2);
            compareTest = userDao.query(newUser2.getUserName());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        // make sure compareTest isn't null
        assertNotNull(compareTest);
        assertEquals(newUser2, compareTest);
    }

    // This user is not in the database
    @Test
    public void queryFail() throws Exception {
        db.clearTables();

        try {
            Connection conn = db.openConnection();
            UserDao userDao = new UserDao(conn);
            userDao.insert(newUser);
            userDao.insert(newUser2);
            assertNull(userDao.query("wrong_user_name"));
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
    }

    @Test
    public void deletePass() throws Exception {
        User compareTest = null;
        db.clearTables();

        try {
            Connection conn = db.openConnection();
            UserDao userDao = new UserDao(conn);
            userDao.insert(newUser);
            userDao.insert(newUser2);
            // make sure they are in there
            userDao.delete();

            compareTest = userDao.query(newUser2.getUserName());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        // make sure compareTest is null
        assertNull(compareTest);
    }

    @Test
    public void insertPass() throws Exception {
        User compareTest = null;
        db.clearTables();

        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            UserDao userDao = new UserDao(conn);
            userDao.insert(newUser);
            //So lets use a find function to get the event that we just put in back out
            compareTest = userDao.query(newUser.getUserName());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        // make sure compareTest isn't null
        assertNotNull(compareTest);
        assertEquals(newUser, compareTest);
    }

    @Test
    public void insertFail() throws Exception {
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            UserDao userDao = new UserDao(conn);
            //if we call the function the first time it will insert it successfully
            userDao.insert(newUser);
            //again will cause the function to throw an exception
            userDao.insert(newUser);
            db.closeConnection(didItWork);
        } catch (DataAccessException e) {
            //false to show that our function failed to perform correctly
            db.closeConnection(false);
            didItWork = false;
        }

        assertFalse(didItWork);
        User compareTest = newUser;
        try {
            Connection conn = db.openConnection();
            UserDao eDao = new UserDao(conn);
            compareTest = eDao.query(newUser.getUserName());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //Now make sure that compareTest is indeed null
        assertNull(compareTest);
    }
}