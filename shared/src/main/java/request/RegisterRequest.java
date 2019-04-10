package request;

/** The purpose of this class is to create a Java object that holds the request
 * information for the API call to /user/register */
public class RegisterRequest {
    /** This varibale is a Non-empty string that represents the username of the user */
    private String userName;
    /** This varibale is a Non-empty string that represents the password of the user */
    private String password;
    /** This varibale is a Non-empty string that represents the email of the user */
    private String email;
    /** This varibale is a Non-empty string that represents the first name of the user */
    private String firstName;
    /** This varibale is a Non-empty string that represents the last name of the user */
    private String lastName;
    /** This varibale is a one letter string that is either "f" or "m" */
    private String gender;


    /** Constructor that initializes all variables */
    public RegisterRequest(String userName, String password, String email, String firstName, String lastName, String gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /** Constructor that initializes everything to null */
    public RegisterRequest() {
        this.userName = null;
        this.password = null;
        this.email = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
    }

    /** convert all strings in the class to lower case */
    public void toLowerCase() {
        this.userName = this.userName.toLowerCase();
        this.password = this.password.toLowerCase();
        this.email = this.email.toLowerCase();
        this.firstName = this.firstName.toLowerCase();
        this.lastName = this.lastName.toLowerCase();
        this.gender = this.gender.toLowerCase();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
