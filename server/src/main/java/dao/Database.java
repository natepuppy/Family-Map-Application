package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection conn;

    static {
        try {
            //This is how we set up the driver for our database
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Whenever we want to make a change to our database we will have to open a connection and use
    //Statements created by that connection to initiate transactions
    public Connection openConnection() throws DataAccessException {
        try {
            //The Structure for this Connection is driver:language:path
            //The pathing assumes you start in the root of your project unless given a non-relative path
            final String CONNECTION_URL = "jdbc:sqlite:familymap.sqlite";

            // Open a database connection to the file given in the path
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }

        return conn;
    }

    //When we are done manipulating the database it is important to close the connection. This will
    //End the transaction and allow us to either commit our changes to the database or rollback any
    //changes that were made before we encountered a potential error.

    //IMPORTANT: IF YOU FAIL TO CLOSE A CONNECTION AND TRY TO REOPEN THE DATABASE THIS WILL CAUSE THE
    //DATABASE TO LOCK. YOUR CODE MUST ALWAYS INCLUDE A CLOSURE OF THE DATABASE NO MATTER WHAT ERRORS
    //OR PROBLEMS YOU ENCOUNTER
    public void closeConnection(boolean commit) throws DataAccessException {
        try {
            if (commit) {
                //This will commit the changes to the database
                conn.commit();
            } else {
                //If we find out something went wrong, pass a false into closeConnection and this
                //will rollback any changes we made during this connection
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    public void createTables() throws DataAccessException {

        openConnection();

        try (Statement stmt = conn.createStatement()){
            //First lets open our connection to our database.

            //We pull out a statement from the connection we just established
            //Statements are the basis for our transactions in SQL
            //Format this string to be exaclty like a sql create table command
            String sql1 = "CREATE TABLE IF NOT EXISTS Events ( " +
                    "eventID varchar(255) NOT NULL UNIQUE, " +
                    "descendant varchar(255) NOT NULL, " +
                    "personID varchar(255) NOT NULL, " +
                    "latitude float NOT NULL, " +
                    "longitude float NOT NULL, " +
                    "country varchar(255) NOT NULL, " +
                    "city varchar(255) NOT NULL, " +
                    "eventType varchar(255) NOT NULL, " +
                    "year int NOT NULL, " +
                    "primary key (eventID))";

            String sql2 = "CREATE TABLE IF NOT EXISTS Users ( " +
                    "userName varchar(255) NOT NULL UNIQUE, " +
                    "password1 varchar(255) NOT NULL, " +
                    "email varchar(255) NOT NULL, " +
                    "firstName varchar(255) NOT NULL, " +
                    "lastName varchar(255) NOT NULL, " +
                    "gender varchar(1) NOT NULL, " +
                    "personID varchar(255) NOT NULL, " +
                    "primary key (userName))";

            String sql3 = "CREATE TABLE IF NOT EXISTS Persons ( " +
                    "personID varchar(255) NOT NULL UNIQUE, " +
                    "descendant varchar(255), " +
                    "firstName varchar(255) NOT NULL, " +
                    "lastName varchar(255) NOT NULL, " +
                    "gender varchar(1) NOT NULL, " +
                    "father varchar(255), " +
                    "mother varchar(255), " +
                    "spouse varchar(255), " +
                    "primary key (personID))";

            String sql4 = "CREATE TABLE IF NOT EXISTS AuthTokens ( " +
                    "userName varchar(255) NOT NULL, " +
                    "authToken varchar(255) NOT NULL UNIQUE );";


            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);
            stmt.executeUpdate(sql3);
            stmt.executeUpdate(sql4);
            //if we got here without any problems we succesfully created the table and can commit
            closeConnection(true);
        } catch (DataAccessException e) {
            //if our table creation caused an error, we can just not commit the changes that did happen
            closeConnection(false);
            throw e;
        } catch (SQLException e) {
            //if our table creation caused an error, we can just not commit the changes that did happen
            closeConnection(false);
            throw new DataAccessException("SQL Error encountered while creating tables");
        }


    }

    public void clearTables() throws DataAccessException
    {
        openConnection();

        try (Statement stmt = conn.createStatement()){
            String sql1 = "DELETE FROM Events";
            String sql2 = "DELETE FROM Users";
            String sql3 = "DELETE FROM Persons";
            String sql4 = "DELETE FROM AuthTokens";

            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);
            stmt.executeUpdate(sql3);
            stmt.executeUpdate(sql4);

            closeConnection(true);
        } catch (DataAccessException e) {
            closeConnection(false);
            throw e;
        } catch (SQLException e) {
            closeConnection(false);
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}
