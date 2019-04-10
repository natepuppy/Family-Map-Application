package request;

/** The purpose of this class is to create a Java object that holds the request
 * information for the API call to /fill/[username]/{generations} */
public class FillRequest {
    /** This variable represents the username of a person in the User table. */
    private String userName;
    /** This variable represents is a non-negative integer that represents how
     * many generations to retrieve from the db for a specific User. */
    private int generations;

    /** Constructor that initializes all variables */
    public FillRequest(String userName, int generations) {
        this.userName = userName;
        this.generations = generations;
    }

    /** Constructor that initializes everything to null or zero. */
    public FillRequest() {
        this.userName = null;
        this.generations = 0;
    }

    /** convert all strings in the class to lower case */
    public void toLowerCase() {
        this.userName = this.userName.toLowerCase();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }
}
