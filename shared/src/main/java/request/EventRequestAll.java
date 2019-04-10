package request;

/** The purpose of this class is to create a Java object that holds the request
 * information for the API call to /event */
public class EventRequestAll {
    /** A non-empty String that represents the auth token given to a user. */
    private String authToken;

    /** Constructor that initializes all variables */
    public EventRequestAll(String authToken) {
        this.authToken = authToken;
    }

    /** Constructor that initializes everything to null */
    public EventRequestAll() {
        this.authToken = null;
    }

    /** convert all strings in the class to lower case */
    public void toLowerCase() {
        this.authToken = this.authToken.toLowerCase();
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
