package tests.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import controller.RegistrationController;
import database.DBConnection;
import exceptions.EntityNotFoundException;
import model.Registration;

class TestGetRegistration {

	static UUID id = null;
	static RegistrationController registrationController = new RegistrationController();
	static Registration registration;

	@BeforeAll
	static void setUp() throws IllegalArgumentException, SQLException {
		registration = registrationController.createRegistration("123456789123", "777777777777",
				UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac120002"), null, LocalDate.parse("2022-07-04"),
				LocalDate.parse("2022-07-06"));
		id = registration.getId();
	}

	@Test
	void testRegistrationSuccessfullyRetrieved() throws EntityNotFoundException, SQLException {
		assertEquals(registration.getId(), registrationController.getRegistration(id).getId());
	}

	@Test
	void testRegistrationDoesNotExist() throws EntityNotFoundException {
		Assertions.assertThrows(EntityNotFoundException.class,
				() -> registrationController.getRegistration(UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac121598")));
	}

	@AfterAll
	static void cleanUp() {
		try {
			Statement statement = DBConnection.getInstance().getDBconnection().createStatement();
			statement.executeUpdate("DELETE FROM Registration WHERE Registration.id =" + id.toString());
		} catch (SQLException e) {

		}
	}
}
