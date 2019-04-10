package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

/** The purpose of this class is to interact with the database and commit changes to the User table. */
public class UserDao {
    /** The connection with the database */
    private Connection conn;

    /** Constructor that sets up connection to db  */
    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /** This method creates and adds new data to the Users table.
     *
     * @param   user which is a User object
     * @return  boolean that tells you if the file was committed or not
     * */
    public boolean insert(User user) throws DataAccessException {
        System.out.println("Register Dao");

        boolean commit = true;
        String sql = "INSERT INTO Users (userName, password1, email, firstName, lastName, " +
                "gender, personID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());      // TODO throws exception

            stmt.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DataAccessException("Error encountered while inserting into the database");
        }

        return commit;
    }

    /** This method queries the information in the Users table.
     *
     * @param   userName which is a String representing userName
     * @return  User object of what you were querying for
     * */
    public User query(String userName) throws DataAccessException {
        User user = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE userName = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next() == true) {  // TODO evaluates to false
                user = new User(rs.getString("userName"), rs.getString("password1"),
                        rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("personID"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }


    /** This method deletes information in the Users table.
     *
     * @return  boolean that tells you if the information was successfully deleted
     * */
    public boolean delete() throws DataAccessException {
        boolean commit = true;
        String sql = "DELETE FROM Users;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DataAccessException("Error encountered while deleting from table");
        }
        return commit;
    }


    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
