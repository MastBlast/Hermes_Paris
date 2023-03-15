package tests.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import controller.RegistrationController;
import exceptions.EntityNotFoundException;
import model.Registration;

class TestCreateRegistration {

	RegistrationController registrationController = new RegistrationController();

	@Test
	void testValidRegistration() throws IllegalArgumentException, SQLException {
		Registration validRegistration = registrationController.createRegistration("123456789123", "777777777777",
				UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac120002"), null, LocalDate.parse("2022-07-04"),
				LocalDate.parse("2022-07-06"));
		try {
			Assertions.assertEquals(validRegistration.getId(),
					registrationController.getRegistration(validRegistration.getId()).getId());
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testTooLongCustomerID() throws IllegalArgumentException {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> registrationController.createRegistration("12345678912345", null,
						UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac120002"), null, LocalDate.parse("2022-07-04"),
						LocalDate.parse("2022-07-06")));
	}

	@Test
	void testInvalidCustomerIDWithLetters() throws IllegalArgumentException {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> registrationController.createRegistration("46567BR77586", null,
						UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac120002"), null, null, null));
	}

	@Test
	void testTooShortCustomerID() throws IllegalArgumentException {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> registrationController.createRegistration("573689", null,
						UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac120002"), null, null, null));
	}

	@Test
	void testTooLongEmployeeID() throws IllegalArgumentException {
		Assertions.assertThrows(IllegalArgumentException.class, () -> registrationController
				.createRegistration("12345678912345", "356745678424567", null, null, null, null));
	}

	@Test
	void testInvalidEmployeeIDWithLetters() throws IllegalArgumentException {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> registrationController.createRegistration("12345678912345", "45477hT34636",
						UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac120002"), null, null, null));
	}

	@Test
	void testTooShortEmployeeID() throws IllegalArgumentException {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> registrationController.createRegistration("12345678912345", "723456",
						UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac120002"), null, null, null));
	}

	@Test
	void testInvalidStartDateTooEarly() throws IllegalArgumentException {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> registrationController.createRegistration("12345678912345", "723456",
						UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac120002"), null, LocalDate.parse("2021-08-06"),
						LocalDate.parse("2021-08-09")));
	}

	@Test
	void testInvalidEndDateTooEarly() throws IllegalArgumentException {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> registrationController.createRegistration("12345678912345", "723456", null, null,
						LocalDate.parse("2022-07-02"), LocalDate.parse("2022-06-03")));
	}

	@Test
	void testInvalidDateRangeLongerThanSeven() throws IllegalArgumentException {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> registrationController.createRegistration("12345678912345", "723456",
						UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac120002"), null, LocalDate.parse("2022-07-07"),
						LocalDate.parse("2022-07-15")));
	}

	@Test
	void testInvalidStoreID() throws IllegalArgumentException {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> registrationController.createRegistration("12345678912345", "777777777777",
						UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac120012"), null, null, null));
	}
}
