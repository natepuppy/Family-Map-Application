package service;

import java.sql.Connection;
import java.util.UUID;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.UserDao;
import model.AuthToken;
import model.User;
import request.LoginRequest;
import result.LoginResult;
import result.RegisterResult;

/** This purpose of this class is to interact with the database through Dao's
 * in order to allow a client to login. */
public class LoginService {
    /** Empty constructor that doesn't initialize any variables */
    public LoginService() {
    }

    /** This function is the service for the /user/login API endpoint. It processes the
     * request and returns the result for this specific API call.
     *
     * @param request which is a LoginRequest object that contains all of the info for a request
     * @return LoginResult Object
     * */
    public LoginResult login(LoginRequest request) {
        LoginResult result = new LoginResult();
        // create instance of database
        Database db = new Database();

        try {
            // open database connection
            Connection conn = db.openConnection();

            UserDao userDao = new UserDao(conn);
            User user = userDao.query(request.getUserName());

            if (user == null) {
                result.setErrorMessage("Invalid userName");
                return result;
            }

            if (!user.getPassword().equals(request.getPassword())) {
                result.setErrorMessage("Request property missing or has invalid value");
                return result;
            }
            // Create new AuthToken
            String uuid = UUID.randomUUID().toString().toLowerCase();
            String userName = user.getUserName();
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            // Insert AuthToken
            AuthToken token = new AuthToken(userName, uuid);
            authTokenDao.insert(token);

            result.setAuthToken(uuid);
            result.setPersonID(user.getPersonID());
            result.setUserName(user.getUserName());
        }
        catch(DataAccessException e) {
            result.setErrorMessage("Internal server error");
            return result;
        }
        finally {
            try {
                db.closeConnection(true);
            }
            catch (DataAccessException e) {
                result.setErrorMessage("Internal server error");
            }
        }
        return result;
    }
}
