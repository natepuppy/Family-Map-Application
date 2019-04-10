package request;

/** The purpose of this class is to create a Java object that holds the request
 * information for the API call to /user/login */
public class LoginRequest {
    /** This varibale is a Non-empty string that represents the username of the user */
    private String userName;
    /** This varibale is a Non-empty string that represents the password of the user */
    private String password;

    /** Constructor that initializes all variables */
    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /** Constructor that initializes everything to null */
    public LoginRequest() {
        this.userName = null;
        this.password = null;
    }

    /** convert all strings in the class to lower case */
    public void toLowerCase() {
        this.userName = this.userName.toLowerCase();
        this.password = this.password.toLowerCase();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
