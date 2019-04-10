package dao;

import model.Event;
import java.sql.Connection;
import org.junit.*;
import static org.junit.Assert.*;

public class EventDaoTest {
    Database db;
    Event event1;
    Event event2;

    @Before
    public void setUp() throws Exception {
        // create a new database
        db = new Database();
        //and new events with random data
        event1 = new Event("Biking_123A", "Gale", "Gale123A",
                10.3f, 10.3f, "Japan", "Ushiku",
                "Biking_Around", 2020);
        event2 = new Event("my_birth_1234", "username", "myID",
                1.3f, 60.45f, "US", "Phoenix",
                "birth", 1996);

        db.createTables();
    }

    @After
    public void tearDown() throws Exception {
        db.clearTables();
    }

    @Test
    public void insertPass() throws Exception {
        Event compareTest = null;
        db.clearTables();
        try {
            //Let's get our connection and make a new DAO
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.insert(event1);
            // lets use a find function to get the event that we just put in back out
            compareTest = eDao.query(event1.getEventID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNotNull(compareTest);
        assertEquals(event1, compareTest);
    }

    @Test
    public void insertFail() throws Exception {
        //lets do this test again but this time lets try to make it fail
        boolean didItWork = true;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            eDao.insert(event1);
            eDao.insert(event1);
            db.closeConnection(didItWork);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }
        //Check to make sure that we did in fact enter our catch statement
        assertFalse(didItWork);
        Event compareTest = event1;
        try {
            Connection conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            compareTest = eDao.query(event1.getEventID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        //Now make sure that compareTest is indeed null
        assertNull(compareTest);

    }

    @Test
    public void queryPass() throws Exception {
        Event compareTest = null;
        db.clearTables();

        try {
            Connection conn = db.openConnection();
            EventDao dao = new EventDao(conn);
            dao.insert(event1);
            dao.insert(event2);
            compareTest = dao.query(event2.getEventID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        // make sure compareTest isn't null
        assertNotNull(compareTest);
        assertEquals(event2, compareTest);
    }

    @Test
    public void queryFail() throws Exception {
        db.clearTables();

        try {
            Connection conn = db.openConnection();
            EventDao dao = new EventDao(conn);
            dao.insert(event1);
            dao.insert(event2);
            assertNull(dao.query("wrong_ID"));
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
    }

    @Test
    public void deletePass() throws Exception {
        Event compareTest = null;
        db.clearTables();

        try {
            Connection conn = db.openConnection();
            EventDao dao = new EventDao(conn);
            dao.insert(event1);
            dao.insert(event2);
            dao.delete();
            compareTest = dao.query(event2.getEventID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(compareTest);
    }

    // test deleting for a specific descendant
    @Test
    public void getForSpecificUserPass() throws Exception {
        Event compareTest = null;
        db.clearTables();
        Event[] events = null;

        try {
            Connection conn = db.openConnection();
            EventDao dao = new EventDao(conn);
            dao.insert(event1);
            dao.insert(event2);
            events = dao.getAllForSpecificUser(event1.getDescendant());
            compareTest = dao.query(event1.getEventID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertEquals(events[0], compareTest);
    }

    // test deleting for a specific descendant
    @Test
    public void getForSpecificUserFail() throws Exception {
        db.clearTables();
        Event[] events = null;

        try {
            Connection conn = db.openConnection();
            EventDao dao = new EventDao(conn);
            dao.insert(event1);
            dao.insert(event2);
            events = dao.getAllForSpecificUser("wrong_descendant");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertEquals(events.length, 0);
    }
}