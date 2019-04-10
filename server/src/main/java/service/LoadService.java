package service;

import java.sql.Connection;
import dao.*;

import model.Event;
import model.Person;
import model.User;

import request.LoadRequest;
import result.LoadResult;

/** This purpose of this class is to interact with the database through Dao's in
 * order to clear the database, and then to load more data into it. */
public class LoadService {
    /** Empty constructor that doesn't initialize any variables */
    public LoadService() {
    }

    /** This function is the service for the /load API endpoint. It processes the
     * request and returns the result for this specific API call.
     *
     * @return LoadResult Object
     * @param request which is a LoadRequest object that contains all of the info for a request
     * */
    public LoadResult load(LoadRequest request) throws Exception {
        LoadResult result = new LoadResult();
        // create instance of database
        Database db = new Database();

        // clear database
        ClearService cs = new ClearService();
        cs.clear();

        try {
            // open database connection
            Connection conn = db.openConnection();
            try {
                addUsers(request.getUsers(), conn);
                addPersons(request.getPersons(), conn);
                addEvents(request.getEvents(), conn);
            }
            catch(DataAccessException e) {
                result.setErrorMessage("Invalid request data");
                db.closeConnection(false);
                return result;
            }

            result.setMessage("Successfully added " + request.getUsers().length + " users, "
                    + request.getPersons().length + " persons, and " + request.getEvents().length
                    + " events to the database.");
        }
        catch (DataAccessException e) {
            result.setErrorMessage("Internal server error");
            db.closeConnection(false);
            return result;
        }
        db.closeConnection(true);
        return result;
    }

    private boolean addUsers(User[] users, Connection conn) throws DataAccessException {
        UserDao dao = new UserDao(conn);
        for (int i = 0; i < users.length; i++) {
            dao.insert(users[i]);
        }

        return true;
    }
    private boolean addPersons(Person[] persons, Connection conn) throws DataAccessException {
        PersonDao dao = new PersonDao(conn);
        for (int i = 0; i < persons.length; i++) {
            dao.insert(persons[i]);
        }

        return true;
    }
    private boolean addEvents(Event[] events, Connection conn) throws DataAccessException {
        EventDao dao = new EventDao(conn);
        for (int i = 0; i < events.length; i++) {
            dao.insert(events[i]);
        }

        return true;
    }
}