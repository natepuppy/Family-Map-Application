package service;

import java.sql.Connection;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.EventDao;
import dao.UserDao;

import model.AuthToken;
import model.Event;
import model.User;

import request.EventRequestAll;
import request.EventRequestOne;
import result.EventResultAll;
import result.EventResultOne;

/** This purpose of this class is to interact with the database through Dao's
 * in order to retrieve Events for a client */
public class EventService {
    /** Empty constructor that doesn't initialize any variables */
    public EventService() {
    }

    /** This function is the service for the /event/[eventID] API endpoint. It processes the
     * request and returns the result for this specific API call.
     *
     * @param request which holds all info for requesting one event
     * @return EventResultOne Object
     * */
    public EventResultOne oneEvent(EventRequestOne request) throws Exception {
        EventResultOne result = new EventResultOne();
        // create instance of database
        Database db = new Database();

        try {
            // open database connection
            Connection conn = db.openConnection();

            // check for authToken
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            AuthToken token = authTokenDao.query(request.getAuthToken().toLowerCase());
            if (token == null) {
                result.setErrorMessage("Invalid auth token");
                db.closeConnection(false);
                return result;
            }

            // check for user
            UserDao userDao = new UserDao(conn);
            User user = userDao.query(token.getUserName());
            if (user == null) {
                result.setErrorMessage("Internal server error");
                db.closeConnection(false);
                return result;
            }

            // check for event
            EventDao eventDao = new EventDao(conn);
            Event event = eventDao.query(request.getEventID());
            if (event == null) {
                result.setErrorMessage("Invalid eventID parameter");
                db.closeConnection(false);
                return result;
            }

            if (!event.getDescendant().equals(user.getUserName())) {
                result.setErrorMessage("requested event does not belong to this user");
                db.closeConnection(false);
                return result;
            }

            result = setEvent(event, result);
            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            result.setErrorMessage("Internal server error");
            db.closeConnection(false);
            return result;
        }
        return result;
    }

    /** This function is the service for the /event API endpoint. It processes the
     * request and returns the result for this specific API call.
     *
     * @param request which holds all info for requesting all events
     * @return EventResultAll Object
     * */
    public EventResultAll allEvents(EventRequestAll request) throws Exception {
        EventResultAll result = new EventResultAll();
        // create instance of database
        Database db = new Database();

        try {
            // open database connection
            Connection conn = db.openConnection();

            // check for authToken
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            AuthToken token = authTokenDao.query(request.getAuthToken());
            if (token == null) {
                result.setErrorMessage("Invalid auth token");
                db.closeConnection(false);
                return result;
            }

            EventDao eventDao = new EventDao(conn);
            Event[] arrEvents = eventDao.getAllForSpecificUser(token.getUserName());
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

    private EventResultOne setEvent(Event event, EventResultOne result) {
        result.setPersonID(event.getPersonID());
        result.setDescendant(event.getDescendant());
        result.setEventID(event.getEventID());
        result.setLatitude(event.getLatitude());
        result.setLongitude(event.getLongitude());
        result.setCountry(event.getCountry());
        result.setCity(event.getCity());
        result.setEventType(event.getEventType());

        return result;
    }
}
