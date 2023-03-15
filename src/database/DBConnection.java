package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
  private static DBConnection instance = null;
  private Connection connection;

  private final String DRIVER = "jdbc:sqlserver://hildur.ucn.dk:1433";
  private final String DATABASE_NAME = ";databseName=CSC-CSD-S211_10407577";
  private final String USER_NAME = ";user=CSC-CSD-S211_10407577";
  private final String PASSWORD = ";password=Password1!";
  private final String ENCRYPTION = ";encrypt=false";

  private DatabaseMetaData dma;

  /**
   * Constructor for DBConnection.
   */
  private DBConnection() {
    String url = DRIVER + DATABASE_NAME + USER_NAME + PASSWORD + ENCRYPTION;

    try {
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      System.out.println("Driver loaded ok");
    } catch (ClassNotFoundException e) {
      System.out.println("Couldn't find the driver");
      System.out.println(e.getMessage());
      throw new RuntimeException("Couldn't find the driver");
    }

    try {
      connection = DriverManager.getConnection(url);
      connection.setAutoCommit(true);
      dma = connection.getMetaData();
      System.out.println("Connection to " + dma.getURL());
      System.out.println("Driver " + dma.getDriverName());
      System.out.println("Database product name " + dma.getDatabaseProductName());
    } catch (SQLException e) {
      System.out.println(e.getMessage());
      System.out.println(url);
      throw new RuntimeException("Couldn't connect to the database");
    }
  }

  /**
   * Get the instance of the DBConnection.
   *
   * @return the instance of the DBConnection
   */
  public static DBConnection getInstance() {
    if (instance == null) {
      instance = new DBConnection();
    }
    return instance;
  }

  /**
   * Get the connection.
   *
   * @return the connection
   */
  public Connection getDBconnection() {
    return connection;
  }

  /**
   * Close the connection.
   */
  public void closeConnection() {
    try {
      connection.close();
      instance = null;
      System.out.println("The connection is closed");
    } catch (Exception e) {
      System.out.println("Error trying to close the database " + e.getMessage());
    }
  }
}
