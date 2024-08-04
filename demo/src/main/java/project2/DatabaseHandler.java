package project2;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Handles database connections and operations for the application.
 * @author Vittoria Cicalese
 */

public class DatabaseHandler {

    private Connection conn = null;

     /**
     * Gets the current database connection.
     * 
     * @return The current database connection.
     */
    public Connection getConn(){
        return this.conn;
    }

    /**
     * Attempts to establish a database connection when an instance of DatabaseHandler is created.
     */
    public DatabaseHandler() {
        connectToDatabase();
    }

    /**
     * Establishes a connection to the database using credentials from the db.config configuration file.
     */
    public void connectToDatabase() {
        String databaseUrl = "jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315_903_01_db"; 
        
        Properties prop = new Properties();
        try {
            try {
                prop.load(new FileInputStream("src/main/java/project2/db.config"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        
            String user = prop.getProperty("db.user");
            String pswd = prop.getProperty("db.password");
    
            conn = DriverManager.getConnection(databaseUrl, user, pswd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the current database connection.
     */
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Failed to close database connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
   

   /**
     * Executes an SQL command that does not return a ResultSet (e.g., INSERT, UPDATE, DELETE).
     * 
     * @param sql The SQL command to execute.
     * @return True if the command was executed successfully, false otherwise.
     */
    public boolean executeSQLCommand(String sql) {
        try (Statement statement = conn.createStatement()) {
            statement.execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fetches data from the database for a given SQL query.
     * 
     * @param sql The SQL query to execute.
     * @param columnNames A list of column names to fetch from the ResultSet.
     * @return A list of rows, with each row represented as a list of strings.
     */
    public List<List<String>> fetchData(String sql, List<String> columnNames) {
        List<List<String>> data = new ArrayList<>();
        try (Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                List<String> row = new ArrayList<>();
                for (String columnName : columnNames) {
                    row.add(rs.getString(columnName));
                }
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    
}