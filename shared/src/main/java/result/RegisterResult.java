package result;

import java.util.Objects;

/** The purpose of this class is to hold the contents of the result of an API call to /user/register */
public class RegisterResult {
    /** Non-empty auth token string */
    private String authToken;
    /** User name passed in with request */
    private String userName;
    /** Non-empty string containing the Person ID of the user’s generated Person object */
    private String personID;
    /** This variable is a String that holds the description of the error that occurred (if there was one) */
    private String errorMessage;

    /** Constructor with no parameters. All members are initialized to null. */
    public RegisterResult() {
        this.authToken = null;
        this.userName = null;
        this.personID = null;
        this.errorMessage = null;
    }

    /** Constructor with all variables as parameters. Members are initialized to whatever values are passed in. */
    public RegisterResult(String authToken, String userName, String personID, String errorMessage) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
        this.errorMessage = errorMessage;
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

    public String getErrorMessage() { return errorMessage; }

    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterResult that = (RegisterResult) o;
        return Objects.equals(authToken, that.authToken) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(personID, that.personID) &&
                Objects.equals(errorMessage, that.errorMessage);
    }
}
