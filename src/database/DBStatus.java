package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import gui.events.EventHandler;

public class DBStatus extends Thread {
  private final String CHECK_CONNECTION_QUERY = "SELECT 1";
  private boolean isConnectionAlive = true;

  /**
   * Checks if the connection is alive every 5 seconds.
   */
  public void run() {
    while (true) {
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      isConnectionAlive = checkConnection();
      notifyConnectionStatus();
    }
  }

  /**
   * Checks if the connection is alive.
   */
  private boolean checkConnection() {
    Connection connection = DBConnection.getInstance().getDBconnection();
    try {
      PreparedStatement ps = connection.prepareStatement(CHECK_CONNECTION_QUERY, Statement.RETURN_GENERATED_KEYS);

      ResultSet rs = ps.executeQuery();
      rs.next();

      if (rs.getInt(1) == 1) {
        return true;
      } else {
        return false;
      }
    } catch (SQLException e) {
      DBConnection.getInstance().closeConnection();
      return false;
    }
  }

  /**
   * Notifies the EventHandler about the connection status.
   */
  private void notifyConnectionStatus() {
    EventHandler.fireEvent("db-connection-status-changed", isConnectionAlive);
  }
}
