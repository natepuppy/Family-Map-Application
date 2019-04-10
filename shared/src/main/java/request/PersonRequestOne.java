package request;

/** The purpose of this class is to create a Java object that holds the request
 * information for the API call to /person/[personID] */
public class PersonRequestOne {
    /** A non-empty String that represents the auth token given to a user. */
    private String authToken;
    /** Person’s unique ID (non-empty String) */
    private String personID;

    /** Constructor that initializes all variables */
    public PersonRequestOne(String authToken, String personID) {
        this.authToken = authToken;
        this.personID = personID;
    }

    /** Constructor that initializes everything to null */
    public PersonRequestOne() {
        this.authToken = null;
        this.personID = null;
    }

    /** convert all strings in the class to lower case */
    public void toLowerCase() {
        this.authToken = this.authToken.toLowerCase();
        this.personID = this.personID.toLowerCase();
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}