package dao;

import org.junit.*;

import java.sql.Connection;

import model.Person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

//We will use this to test that our insert function is working and failing in the right ways
public class PersonDaoTest {
    Database db;
    Person newPerson;
    Person newPerson2;

    @Before
    public void setUp() throws Exception {
        db = new Database();
        newPerson = new Person("nmcla274", "children", "nathan", "clark", "m", "mike", "michelle", "fingersCrossed");
        newPerson2 = new Person("bilbo_baggins", "children", "nathan", "clark", "m", "mike", "michelle", "fingersCrossed");
        db.createTables();
    }

    @After
    public void tearDown() throws Exception {
        db.clearTables();
    }

    // this runs a successful query
    @Test
    public void queryPass() throws Exception {
        Person compareTest = null;
        db.clearTables();

        try {
            Connection conn = db.openConnection();
            PersonDao personDao = new PersonDao(conn);
            personDao.insert(newPerson);
            personDao.insert(newPerson2);
            compareTest = personDao.query(newPerson2.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        // make sure compareTest isn't null
        assertNotNull(compareTest);
        assertEquals(newPerson2, compareTest);
    }

    // person is not in db, thats why it fails
    @Test
    public void queryFail() throws Exception {
        db.clearTables();

        try {
            Connection conn = db.openConnection();
            PersonDao personDao = new PersonDao(conn);
            personDao.insert(newPerson);
            personDao.insert(newPerson2);
            assertNull(personDao.query("wrong_ID"));
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
    }

    // deletes everything
    @Test
    public void deletePass() throws Exception {
        Person compareTest = null;
        db.clearTables();

        try {
            Connection conn = db.openConnection();
            PersonDao personDao = new PersonDao(conn);
            personDao.insert(newPerson);
            personDao.insert(newPerson2);

            personDao.delete();

            compareTest = personDao.query(newPerson2.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        // make sure compareTest is null
        assertNull(compareTest);
    }

    // Insert a person
    @Test
    public void insertPass() throws Exception {
        Person compareTest = null;
        db.clearTables();

        try {
            Connection conn = db.openConnection();
            PersonDao personDao = new PersonDao(conn);
            personDao.insert(newPerson);
            compareTest = personDao.query(newPerson.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNotNull(compareTest);
        assertEquals(newPerson, compareTest);
    }

    // test to see if you can't insert someone with the same personID twice
    @Test
    public void insertFail() throws Exception {
        boolean didItWork = true;
        db.clearTables();

        try {
            Connection conn = db.openConnection();
            PersonDao personDao = new PersonDao(conn);
            personDao.insert(newPerson);
            personDao.insert(newPerson);
            db.closeConnection(didItWork);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            didItWork = false;
        }

        assertFalse(didItWork);
        Person compareTest = newPerson;
        try {
            Connection conn = db.openConnection();
            PersonDao personDao = new PersonDao(conn);
            compareTest = personDao.query(newPerson.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertNull(compareTest);
    }

    // test deleting for a specific descendant
    @Test
    public void getForSpecificUserPass() throws Exception {
        Person compareTest = null;
        db.clearTables();
        Person[] persons = null;

        try {
            Connection conn = db.openConnection();
            PersonDao dao = new PersonDao(conn);
            dao.insert(newPerson);
            dao.insert(newPerson2);

            persons = dao.getAllForSpecificUser(newPerson.getDescendant());
            compareTest = dao.query(newPerson.getPersonID());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertEquals(persons[0], compareTest);
    }

    // test deleting for a specific descendant
    @Test
    public void getForSpecificUserFail() throws Exception {
        db.clearTables();
        Person[] persons = null;

        try {
            Connection conn = db.openConnection();
            PersonDao dao = new PersonDao(conn);
            dao.insert(newPerson);
            dao.insert(newPerson2);
            persons = dao.getAllForSpecificUser("wrong_descendant");
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
        }
        assertEquals(persons.length, 0);
    }
}