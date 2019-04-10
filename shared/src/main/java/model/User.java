package model;

import java.util.Objects;

/** The purpose of this class is to hold the data that is accessed from the User table in the database. */
public class User {
    /** Unique user name (non-empty string) */
    private String userName;
    /** User’s password (non-empty string) */
    private String password;
    /** User’s email address (non-empty string) */
    private String email;
    /** User’s first name (non-empty string) */
    private String firstName;
    /**  User’s last name (non-empty string) */
    private String lastName;
    /** User’s gender (string: “f” or “m”) */
    private String gender;
    /** Unique Person ID assigned to this user’s generated Person object */
    private String personID;

    /** Constructor with no parameters. All members are initialized to null. */
    public User() {
        this.userName = null;
        this.password = null;
        this.email = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.personID = null;
    }

    /** Constructor with all variables as parameters. Members are initialized to whatever values are passed in. */
    public User(String userName, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    /** convert all strings in the class to lower case */
    public void toLowerCase() {
        if (this.personID != null) this.personID = this.personID.toLowerCase();
        if (this.firstName != null) this.firstName = this.firstName.toLowerCase();
        if (this.lastName != null) this.lastName = this.lastName.toLowerCase();
        if (this.gender != null) this.gender = this.gender.toLowerCase();
        if (this.userName != null) this.userName = this.userName.toLowerCase();
        if (this.password != null) this.password = this.password.toLowerCase();
        if (this.email != null) this.email = this.email.toLowerCase();
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(gender, user.gender) &&
                Objects.equals(personID, user.personID);
    }
}
