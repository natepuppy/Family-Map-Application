package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Person;

/** The purpose of this class is to interact with the database and commit changes to the Person table. */
public class PersonDao {
    /** The connection with the database */
    private Connection conn;

    /** Constructor that sets up connection to db  */
    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /** This method creates and adds new data to the Persons table.
     *
     * @param   person which is a Person object
     * @return  boolean that tells you if the file was committed or not
     * */
    public boolean insert(Person person) throws DataAccessException {
        boolean commit = true;
        String sql = "INSERT INTO Persons (personID, descendant, firstName, lastName, gender, " +
                "father, mother, spouse) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getDescendant());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFather());
            stmt.setString(7, person.getMother());
            stmt.setString(8, person.getSpouse());

            stmt.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DataAccessException("Error encountered while inserting into the database");
        }
        return commit;
    }

    /** This method queries the information in the Persons table.
     *
     * @param   personID which is a String representing personID
     * @return  Person object of what you were querying for
     * */
    public Person query(String personID) throws DataAccessException {
        Person person = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next() == true) {
                person = new Person(rs.getString("personID"), rs.getString("descendant"),
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("father"),
                        rs.getString("mother"), rs.getString("spouse"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
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

    /** This method queries the information in the Persons table with a specific descendant
     *
     * @param   descendent which is a String representing the userName column
     * @return  Person array of what you were querying for
     * */
    public Person[] getAllForSpecificUser(String descendent) throws DataAccessException {
        ArrayList<Person> persons = new ArrayList<Person>();
        Person[] array;
        Person person = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE descendant = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, descendent);
            rs = stmt.executeQuery();

            while (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("descendant"), rs.getString("firstName"),
                        rs.getString("lastName"), rs.getString("gender"), rs.getString("father"),
                        rs.getString("mother"), rs.getString("spouse"));
                persons.add(person);
            }
            array = persons.toArray(new Person[persons.size()]);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
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

    /** This method deletes information in the Persons table.
     *
     * @return  boolean that tells you if the information was successfully deleted
     * */
    public boolean delete() throws DataAccessException {
        boolean commit = true;
        String sql = "DELETE FROM Persons;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            commit = false;
            throw new DataAccessException("Error encountered while deleting from table");
        }
        return commit;
    }

    /** This method deletes information in the Persons table for specified user.
     *
     * @param userName of persons data you want to delete
     * @return  boolean that tells you if the information was successfully deleted
     * */
    public boolean delete(String userName) throws DataAccessException {
        boolean commit = true;
        String sql = "DELETE FROM Persons WHERE descendant = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName);
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






