package model;

import java.util.Objects;

/** The purpose of this class is to hold the data that is accessed from the AuthToken table in the database. */
public class AuthToken {
    /** Unique user name (non-empty string) */
    private String userName;
    /** A unique String that represents the user, and is used to verify the user's identity. */
    private String authToken;

    /** Constructor with no parameters. All members are initialized to null. */
    public AuthToken() {
        this.userName = null;
        this.authToken = null;
    }

    /** Constructor with all variables as parameters. Members are initialized to whatever values are passed in. */
    public AuthToken(String userName, String authToken) {
        this.userName = userName;
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken1 = (AuthToken) o;
        return Objects.equals(userName, authToken1.userName) &&
                Objects.equals(authToken, authToken1.authToken);
    }
}


