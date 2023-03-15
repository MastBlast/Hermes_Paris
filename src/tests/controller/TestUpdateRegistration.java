package tests.controller;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import controller.RegistrationController;

class TestUpdateRegistration {
  UUID id = UUID.fromString("00aaced3-b7ec-4e5b-98b9-0d790b4fb42b");
  RegistrationController registrationController = new RegistrationController();

  // @Test
  // void testValidRegistration() throws IllegalArgumentException, EntityNotFoundException {
  //   registrationController.updateRegistration(id, "123456789123", "777777777777",
  //       UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac120002"), null, LocalDate.parse("2022-07-04"),
  //       LocalDate.parse("2022-07-06"));
  // }

  @Test
  void testTooLongEmployeeID() throws IllegalArgumentException {
    Assertions.assertThrows(IllegalArgumentException.class, () -> registrationController.updateRegistration(id,
        "12345678912345", "356745678424567", null, null, null, null));
  }

  @Test
  void testInvalidEmployeeIDWithLetters() throws IllegalArgumentException {
    Assertions.assertThrows(IllegalArgumentException.class, () -> registrationController.updateRegistration(id,
        "12345678912345", "45477hT34636", UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac120002"), null, null, null));
  }

  @Test
  void testTooShortEmployeeID() throws IllegalArgumentException {
    Assertions.assertThrows(IllegalArgumentException.class, () -> registrationController.updateRegistration(id,
        "12345678912345", "723456", UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac120002"), null, null, null));
  }

  @Test
  void testInvalidStartDateTooEarly() throws IllegalArgumentException {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> registrationController.updateRegistration(id, "12345678912345", "723456",
            UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac120002"), null, LocalDate.parse("2021-08-06"),
            LocalDate.parse("2021-08-09")));
  }

  @Test
  void testInvalidEndDateTooEarly() throws IllegalArgumentException {
    Assertions.assertThrows(IllegalArgumentException.class, () -> registrationController.updateRegistration(id,
        "12345678912345", "723456", null, null, LocalDate.parse("2022-07-02"), LocalDate.parse("2022-06-03")));
  }

  @Test
  void testInvalidDateRangeLongerThanSeven() throws IllegalArgumentException {
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> registrationController.updateRegistration(id, "12345678912345", "723456",
            UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac120002"), null, LocalDate.parse("2022-07-07"),
            LocalDate.parse("2022-07-15")));
  }

  @Test
  void testInvalidStoreID() throws IllegalArgumentException {
    Assertions.assertThrows(IllegalArgumentException.class, () -> registrationController.updateRegistration(id,
        "12345678912345", "777777777777", UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac120012"), null, null, null));
  }
}
