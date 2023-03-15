package tests.database;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import database.DBConnection;

public class TestDBConnection {
	@Test
	public void testDBConnection() {
		try {
			DBConnection.getInstance().getDBconnection();
			assertTrue(true);
		} catch (RuntimeException e) {
			assertTrue(false);
		}
	}

  @AfterAll
  public static void closeConnection() {
    DBConnection.getInstance().closeConnection();;
  }
}
