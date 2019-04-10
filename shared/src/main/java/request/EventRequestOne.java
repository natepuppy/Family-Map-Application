package request;

/** The purpose of this class is to create a Java object that holds the request
 * information for the API call to /event/[eventID] */
public class EventRequestOne {
    /** A non-empty String that represents the auth token given to a user. */
    private String authToken;
    /**  Event’s unique ID (non-empty string). */
    private String eventID;

    /** Constructor that initializes all variables */
    public EventRequestOne(String authToken, String eventID) {
        this.authToken = authToken;
        this.eventID = eventID;
    }

    /** Constructor that initializes everything to null */
    public EventRequestOne() {
        this.authToken = null;
        this.eventID = null;
    }

    /** convert all strings in the class to lower case */
    public void toLowerCase() {
        this.authToken = this.authToken.toLowerCase();
        this.eventID = this.eventID.toLowerCase();
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
}
