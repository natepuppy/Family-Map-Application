package service;

import java.sql.Connection;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.EventDao;
import dao.PersonDao;
import dao.UserDao;
import result.ClearResult;

/** This purpose of this class is to interact with the database through Dao's in order to clear info for a client */
public class ClearService {
    /** Empty constructor that doesn't initialize any variables */
    public ClearService() { }

    /** This function is the service for the /clear API endpoint. It processes the
     * request and returns the result for this specific API call.
     *
     * @return ClearResult Object
     * */
    public ClearResult clear() {
        // Internal server error
        ClearResult result = new ClearResult();

        // create instance of database
        Database db = new Database();

        try {
            // establish connection
            Connection conn = db.openConnection();

            // create daos for each table
            UserDao userDao = new UserDao(conn);
            PersonDao personDao = new PersonDao(conn);
            EventDao eventDao = new EventDao(conn);
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);

            // delete from each database
            userDao.delete();
            personDao.delete();
            eventDao.delete();
            authTokenDao.delete();

            result.setMessage("Clear succeeded.");
        }
        catch (DataAccessException e) {
            result.setErrorMessage("Internal server error");
            return result;
        }
        finally {
            try {
                db.closeConnection(true);
            }
            catch (DataAccessException e) {
                result.setErrorMessage("Internal server error");
            }
        }
        return result;
    }
}
