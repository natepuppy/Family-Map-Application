package service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.PersonDao;
import dao.UserDao;
import model.AuthToken;
import model.Person;
import model.User;
import request.PersonRequestAll;
import request.PersonRequestOne;
import result.PersonResultAll;
import result.PersonResultOne;

/** This purpose of this class is to interact with the database through Dao's in order to get Person info. */
public class PersonService {
    /** Empty constructor that doesn't initialize any variables */
    public PersonService() {
    }

    /** This function is the service for the /person/[personID] API endpoint. It processes the
     * request and returns the result for this specific API call.
     *
     * @param request which holds all info for requesting one person
     * @return PersonResultOne Object
     * */
    public PersonResultOne onePerson(PersonRequestOne request) throws Exception {
        PersonResultOne result = new PersonResultOne();
        // create instance of database
        Database db = new Database();

        try {
            // open database connection
            Connection conn = db.openConnection();

            // check authToken
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            AuthToken token = authTokenDao.query(request.getAuthToken());
            if (token == null) {
                result.setErrorMessage("Invalid auth token");
                db.closeConnection(false);
                return result;
            }

            // check personID parameter
            UserDao userDao = new UserDao(conn);
            User user = userDao.query(token.getUserName());
            if (user == null) {
                result.setErrorMessage("Internal server error");
                db.closeConnection(false);
                return result;
            }

            PersonDao personDao = new PersonDao(conn);
            Person person = personDao.query(request.getPersonID());
            if (person == null) {
                result.setErrorMessage("Invalid personID parameter");
                db.closeConnection(false);
                return result;
            }

            if (!person.getDescendant().equals(user.getUserName())) {
                result.setErrorMessage("Requested person does not belong to this user");
                db.closeConnection(false);
                return result;
            }

            result = setPerson(person, result);
            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            result.setErrorMessage("Internal server error");
            db.closeConnection(false);
            return result;
        }
        return result;
    }


    /** This function is the service for the /person API endpoint. It processes the
     * request and returns the result for this specific API call.
     *
     * @param request which holds all info for requesting all persons
     * @return PersonResultAll Object
     * */
    public PersonResultAll allPersons(PersonRequestAll request) throws Exception {
        PersonResultAll result = new PersonResultAll();
        // create instance of database
        Database db = new Database();

        try {
            // open database connection
            Connection conn = db.openConnection();

            // Check authToken
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            AuthToken token = authTokenDao.query(request.getAuthToken());
            if (token == null) {
                result.setErrorMessage("Invalid auth token");
                db.closeConnection(false);
                return result;
            }

            PersonDao personDao = new PersonDao(conn);
            Person[] arrEvents = personDao.getAllForSpecificUser(token.getUserName());
            result.setData(arrEvents);
            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            result.setErrorMessage("Internal server error");
            db.closeConnection(false);
            return result;
        }
        return result;
    }

    private PersonResultOne setPerson(Person person, PersonResultOne result) {
        result.setPersonID(person.getPersonID());
        result.setDescendant(person.getDescendant());
        result.setFather(person.getFather());
        result.setMother(person.getMother());
        result.setFirstName(person.getFirstName());
        result.setLastName(person.getLastName());
        result.setGender(person.getGender());
        result.setSpouse(person.getSpouse());

        return result;
    }
}
