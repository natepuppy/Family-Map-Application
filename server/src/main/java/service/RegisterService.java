package service;

import dao.DataAccessException;

import java.sql.Connection;
import java.util.UUID;
import dao.*;
import request.FillRequest;
import request.RegisterRequest;
import result.RegisterResult;
import model.*;

/** This purpose of this class is to interact with the database through Dao's in order to register a client */
public class RegisterService {
    /** Empty constructor that doesn't initialize any variables */
    public RegisterService() {
    }

    /** This function is the service for the /user/register API endpoint. It processes the
     * request and returns the result for this specific API call.
     *
     * @param request which is a RegisterRequest object that contains all of the info for a request
     * @return RegisterResult Object
     * */
    public RegisterResult register(RegisterRequest request) throws Exception {
        System.out.println("Register Service");

        RegisterResult result = new RegisterResult();
        // create instance of database
        Database db = new Database();

        try {
            // open database connection
            Connection conn = db.openConnection();
            // create user and authTokens for inserting into db
            UserDao userDao = new UserDao(conn);
            User user = convertFromRequestToUser(request);
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);
            AuthToken token = createToken(user.getUserName());

            // check if user is in database, if they arent register them
            if (userDao.query(user.getUserName()) != null) {
                System.out.println("null");
                result.setErrorMessage("Username already taken by another user");
                db.closeConnection(false);
                return result;
            }
            userDao.insert(user);
            authTokenDao.insert(token);

            // close database
            db.closeConnection(true);

            // call fill for this user
            FillService fs = new FillService();
            FillRequest fillRequest = new FillRequest(request.getUserName(), 4);
            fs.fill(fillRequest);

            // set variables in the result
            result.setAuthToken(token.getAuthToken());
            result.setUserName(user.getUserName());
            result.setPersonID(user.getPersonID());
        }
        catch (DataAccessException e) {
            result.setErrorMessage("Internal server error");
            db.closeConnection(false);
            return result;
        }
        return result;
    }

    private User convertFromRequestToUser(RegisterRequest request) {
        User user = new User();
        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.getGender());
        user.setPersonID(UUID.randomUUID().toString().toLowerCase());

        return user;
    }

    private AuthToken createToken(String userName) {
        AuthToken token = new AuthToken();
        token.setAuthToken(UUID.randomUUID().toString().toLowerCase());
        token.setUserName(userName);

        return token;
    }
}
