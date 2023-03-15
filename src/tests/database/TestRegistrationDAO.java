package tests.database;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import database.DBConnection;
import database.RegistrationDAO;
import exceptions.EntityNotFoundException;
import model.Registration;

class TestRegistrationDAO {
  List<String> productBarcodes  = new ArrayList<>();

  @Test
  public void testInsertRegistration() throws SQLException {
	productBarcodes.add("155144133");
    RegistrationDAO registrationDAO = RegistrationDAO.getInstance();
    Registration registration = new Registration("111111111111", "555555555555",
        UUID.fromString("3aaaaaaa-bbbb-cccc-dddd-2eeeeeeeeeee"), productBarcodes, LocalDate.parse("2022-05-08"),
        LocalDate.parse("2022-05-10"));
    Registration registrationFromDB = null;
    registrationDAO.insert(registration);

    try {
      registrationFromDB = registrationDAO.get(registration.getId());
      assertTrue(registration.getId().equals(registrationFromDB.getId()));
    } catch (EntityNotFoundException e) {
      assertTrue(false);
    }
  }

  @Test
  public void testDeleteRegistration() throws SQLException {
    RegistrationDAO registrationDAO = RegistrationDAO.getInstance();
    Registration registration = new Registration("111111111111", "555555555555",
        UUID.fromString("3aaaaaaa-bbbb-cccc-dddd-2eeeeeeeeeee"), null, LocalDate.parse("2022-05-08"),
        LocalDate.parse("2022-05-10"));

    registrationDAO.insert(registration);

    try {
      registrationDAO.delete(registration.getId());
    } catch (EntityNotFoundException e) {
      assertTrue(false);
    }

    try {
      registrationDAO.get(registration.getId());
      assertTrue(false);
    } catch (EntityNotFoundException e) {
      assertTrue(true);
    }
  }

  @Test
  public void testUpdateRegistration() throws SQLException {
    RegistrationDAO registrationDAO = RegistrationDAO.getInstance();
    Registration registration = new Registration("111111111111", "555555555555",
        UUID.fromString("3aaaaaaa-bbbb-cccc-dddd-2eeeeeeeeeee"), null, LocalDate.parse("2022-05-08"),
        LocalDate.parse("2022-05-10"));

    registrationDAO.insert(registration);

    try {
      Registration registrationFromDB = registrationDAO.get(registration.getId());
      registrationFromDB.setEndDate(LocalDate.parse("2022-05-09"));
      registrationDAO.update(registrationFromDB);
    } catch (EntityNotFoundException e) {
      assertTrue(false);
    }

    try {
      Registration registrationFromDB = registrationDAO.get(registration.getId());
      assertTrue(registrationFromDB.getEndDate().equals(LocalDate.parse("2022-05-09")));
    } catch (EntityNotFoundException e) {
      assertTrue(false);
    }
  }

  @Test
  public void testGetRegistrationById() throws SQLException {
    RegistrationDAO registrationDAO = RegistrationDAO.getInstance();
    Registration registration = new Registration("111111111111", "555555555555",
        UUID.fromString("3aaaaaaa-bbbb-cccc-dddd-2eeeeeeeeeee"), null, LocalDate.parse("2022-05-08"),
        LocalDate.parse("2022-05-10"));

    registrationDAO.insert(registration);

    try {
      Registration registrationFromDB = registrationDAO.get(registration.getId());
      assertTrue(registration.getId().equals(registrationFromDB.getId()));
    } catch (EntityNotFoundException e) {
      assertTrue(false);
    }
  }

  @Test
  public void testGetAllRegistrations() throws SQLException {
    RegistrationDAO registrationDAO = RegistrationDAO.getInstance();
    Registration registration = new Registration("111111111111", "555555555555",
        UUID.fromString("3aaaaaaa-bbbb-cccc-dddd-2eeeeeeeeeee"), null, LocalDate.parse("2022-05-08"),
        LocalDate.parse("2022-05-10"));

    try {
      Statement statement = DBConnection.getInstance().getDBconnection().createStatement();
      statement.executeUpdate("DELETE FROM Appointment; DELETE FROM Registration_Product; DELETE FROM Registration");
    } catch (SQLException e) {
      assertTrue(false);
    }

    registrationDAO.insert(registration);

    assertTrue(registrationDAO.getAll().size() == 1);
  }
  
  @AfterAll
  public static void tearDown() {
    try {
      Statement statement = DBConnection.getInstance().getDBconnection().createStatement();
      statement.executeUpdate("DELETE FROM Appointment; DELETE FROM Registration_Product; DELETE FROM Registration");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
