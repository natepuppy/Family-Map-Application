package result;

import model.Person;

/** The purpose of this class is to hold the contents of the result of an API call to /person */
public class PersonResultAll {
    /** This is an array of Person objects * */
    private Person[] data;
    /** This variable is a String that holds the description of the error that occurred (if there was one) */
    private String errorMessage;

    /** Constructor with all variables as parameters. Members are initialized to whatever values are passed in. */
    public PersonResultAll(Person[] data, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
    }

    /** Constructor with no parameters. All members are initialized to null. */
    public PersonResultAll() {
        this.data = null;
        this.errorMessage = null;
    }

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
