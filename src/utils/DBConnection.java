package utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DBConnection - Implements the Singleton Pattern
 * 
 * This class ensures only one database connection instance exists
 * throughout the application lifecycle, providing centralized
 * database access management and connection pooling.
 */
public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/globemed1?";
    private static final String USER = "root";  
    private static final String PASSWORD = "9#Jc4$kB2ED";

    private Connection connection;
    
    private static DBConnection instance;

    private DBConnection() {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
          
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
    public Connection getConnection() {
        return connection;
    }
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ResultSet SELECT(String query) throws SQLException {
        Connection connection = getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }
    public static ResultSet SELECT(PreparedStatement statement) throws SQLException {
        return statement.executeQuery();
    }
    
    public static ResultSet executeQuery(String query) throws SQLException {
        Connection conn = getInstance().getConnection();
        PreparedStatement statement = conn.prepareStatement(query);
        return statement.executeQuery(); // Caller must close ResultSet and PreparedStatement
    }

    public static ResultSet executeQuery(PreparedStatement statement) throws SQLException {
        return statement.executeQuery(); // Caller must close ResultSet
    }

    public static int executeUpdate(String query) throws SQLException {
        try (Connection conn = getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            return statement.executeUpdate();
        }
    }

    public static int executeUpdate(PreparedStatement statement) throws SQLException {
        try (statement) {
            return statement.executeUpdate();
        }
    }




}
