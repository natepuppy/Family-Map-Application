package result;

/** The purpose of this class is to hold the contents of the result of an API call to /load */
public class LoadResult {
    /** This variable is a String that describes the attributes added to the database.
     * Of the form "Successfully added X users, Y persons, and Z events to the database." */
    private String message;
    /** This variable is a String that holds the description of the error that occurred (if there was one) */
    private String errorMessage;

    /** Constructor with all variables as parameters. Members are initialized to whatever values are passed in. */
    public LoadResult(String message, String errorMessage) {
        this.message = message;
        this.errorMessage = errorMessage;
    }

    /** Constructor with no parameters. All members are initialized to null. */
    public LoadResult() {
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
