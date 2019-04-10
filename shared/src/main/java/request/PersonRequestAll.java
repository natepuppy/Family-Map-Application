package request;

/** The purpose of this class is to create a Java object that holds the request
 * information for the API call to /person */
public class PersonRequestAll {
    /** A non-empty String that represents the auth token given to a user. */
    private String authToken;

    /** Constructor that initializes all variables */
    public PersonRequestAll(String authToken) {
        this.authToken = authToken;
    }

    /** Constructor that initializes everything to null */
    public PersonRequestAll() {
        this.authToken = null;
    }

    /** convert all variable to lower case */
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
