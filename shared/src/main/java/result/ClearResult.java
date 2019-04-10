package result;

/** The purpose of this class is to hold the contents of the result of an API call to /clear */
public class ClearResult {
    /** This variable is a String that describes the attributes added to the database.
     * Of the form "Clear succeeded." */
    private String message;
    /** This variable is a String that holds the description of the error that occurred (if there was one) */
    private String errorMessage;

    /** Constructor with all variables as parameters. Members are initialized to whatever values are passed in. */
    public ClearResult(String message, String errorMessage) {
        this.message = message;
        this.errorMessage = errorMessage;
    }

    /** Constructor with no parameters. All members are initialized to null. */
    public ClearResult() {
        this.message = null;
        this.errorMessage = null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}


/*


/user/register
/user/login
/clear
/fill/[username]/{generations}
/load
/person/[personID]
/person
/event/[eventID]
/event


 */