package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import model.Event;

/** The purpose of this class is to interact with the database and commit changes to the Event table. */
public class EventDao {
    /** The connection with the database */
    private Connection conn;

    /** Constructor that sets up connection to db  */
    public EventDao(Connection conn) {
        this.conn = conn;
    }

    /** This method creates and adds new data to the Events table.
     *
     * @param event which is an Event object
     * @return boolean that tells you if the file was committed or not
     * */
    public boolean insert(Event event) throws DataAccessException {
        boolean commit = true;
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO Events (eventID, descendant, personID, latitude, longitude, " +
                "country, city, eventType, year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getDescendant());
            stmt.setString(3, event.getPersonID());
            stmt.setDouble(4, event.getLatitude());
            stmt.setDouble(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DataAccessException("Error encountered while inserting into the database");
        }

        return commit;
    }

    /** This method queries the information in the Events table.
     *
     * @param   eventID which is a String representing eventID
     * @return  Event object of what you were querying for
     * */
    public Event query(String eventID) throws DataAccessException {
        Event event = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next() == true) {
                event = new Event(rs.getString("eventID"), rs.getString("descendant"),
                        rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                return event;
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

    /** This method queries the information in the Events table with a specific descendant
     *
     * @param   descendent which is a String representing the userName column
     * @return  Event array of what you were querying for
     * */
    public Event[] getAllForSpecificUser(String descendent) throws DataAccessException {
        ArrayList<Event> events = new ArrayList<Event>();
        Event[] array;
        Event event = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE descendant = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descendent);
            rs = stmt.executeQuery();

                while (rs.next()) {
                    event = new Event(rs.getString("eventID"), rs.getString("descendant"),
                            rs.getString("personID"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                            rs.getString("country"), rs.getString("city"), rs.getString("eventType"),
                            rs.getInt("year"));
                    events.add(event);
                }
                array = events.toArray(new Event[events.size()]);

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
        return array;
    }

    /** This method deletes information in the Events table.
     *
     * @return  boolean that tells you if the information was successfully deleted
     * */
    public boolean delete() throws DataAccessException {
        boolean commit = true;
        String sql = "DELETE FROM Events;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DataAccessException("Error encountered while deleting from table");
        }
        return commit;
    }


    /** This method deletes information in the Events table for specified user.
     *
     * @param userName of persons data you want to delete
     * @return  boolean that tells you if the information was successfully deleted
     * */
    public boolean delete(String userName) throws DataAccessException {
        boolean commit = true;
        String sql = "DELETE FROM Events WHERE descendant = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DataAccessException("Error encountered while deleting from table");
        }
        return commit;
    }

    /** This method returns all entries in the Events table.
     *
     * @return  Event object of what you were querying for
     * */
    public Event getAllEvents()  {
        return null;
    }


    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

}


