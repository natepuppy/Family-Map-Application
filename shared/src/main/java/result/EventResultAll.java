package result;

import model.Event;

/** The purpose of this class is to hold the contents of the result of an API call to /event */
public class EventResultAll {
    /** This varibale is an Array of Event objects */
    private Event[] data;
    /** This variable is a String that holds the description of the error that occurred (if there was one) */
    private String errorMessage;

    /** Constructor with all variables as parameters. Members are initialized to whatever values are passed in. */
    public EventResultAll(Event[] data, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
    }

    /** Constructor with no parameters. All members are initialized to null. */
    public EventResultAll() {
        this.data = null;
        this.errorMessage = null;
    }

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
