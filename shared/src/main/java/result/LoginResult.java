package result;

/** The purpose of this class is to hold the contents of the result of an API call to /user/login */
public class LoginResult {
    /** Non-empty auth token string */
    private String authToken;
    /** User name passed in with request */
    private String userName;
    /** Non-empty string containing the Person ID of the user’s generated Person object */
    private String personID;
    /** This variable is a String that holds the description of the error that occurred (if there was one) */
    private String errorMessage;

    /** Constructor with all variables as parameters. Members are initialized to whatever values are passed in. */
    public LoginResult(String authToken, String userName, String personID, String errorMessage) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
        this.errorMessage = errorMessage;
    }

    /** Constructor with no parameters. All members are initialized to null. */
    public LoginResult() {
        this.authToken = null;
        this.userName = null;
        this.personID = null;
        this.errorMessage = null;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
