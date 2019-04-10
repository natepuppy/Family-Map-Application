package dao;

import model.AuthToken;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** The purpose of this class is to interact with the database and commit changes to the AuthToken table. */
public class AuthTokenDao {
    /** The connection with the database */
    private Connection conn;

    /** Constructor that sets up connection to db  */
    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }

    /** This method creates and adds new data to the AuthToken table.
     *
     * @param   token which is an AuthToken representing the token for a specific user
     * @return  boolean that tells you if the file was committed or not
     * */
    public boolean insert(AuthToken token) throws DataAccessException {
        boolean commit = true;
        String sql = "INSERT INTO AuthTokens (userName, authToken) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token.getUserName());
            stmt.setString(2, token.getAuthToken());

            stmt.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DataAccessException("Error encountered while inserting into the database");
        }
        return commit;
    }

    /** This method queries the information in the AuthToken table.
     *
     * @param   token which is the user's unique identifier
     * @return  AuthToken object of what you were querying for
     * */
    public AuthToken query(String token) throws DataAccessException {
        AuthToken authToken = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthTokens WHERE authToken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            rs = stmt.executeQuery();

            if (rs.next() == true) {
                authToken = new AuthToken(rs.getString("userName"), rs.getString("authToken"));
                return authToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
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

    /** This method deletes information in the AuthToken table.
     *
     * @return  boolean that tells you if the information was successfully deleted
     * */
    public boolean delete() throws DataAccessException {
        boolean commit = true;
        String sql = "DELETE FROM AuthTokens;";

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
