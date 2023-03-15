package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

public class DataGenerator {
  /**
   * Generater registrations
   *
   * @throws SQLException, IllegalArgumentException
   */
  public static void createRegistrations() throws IllegalArgumentException, SQLException {
    RegistrationController registrationController = new RegistrationController();

    registrationController.createRegistration("111111111111", "555555555555",
        UUID.fromString("3aaaaaaa-bbbb-cccc-dddd-2eeeeeeeeeee"), null, LocalDate.now().plusDays(1),
        LocalDate.now().plusDays(7));
    registrationController.createRegistration("123456789123", "777777777777",
        UUID.fromString("3BAAABBA-BBBB-CCCC-DDDD-2EEEEEEEEEEE"), null, LocalDate.now().plusDays(2),
        LocalDate.now().plusDays(4));
  }
}
