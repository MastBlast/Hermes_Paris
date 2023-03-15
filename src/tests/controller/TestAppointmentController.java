package tests.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import controller.AppointmentController;
import controller.RegistrationController;
import database.DBConnection;
import database.RegistrationDAO;
import exceptions.EntityNotFoundException;
import model.Appointment;
import model.Registration;

public class TestAppointmentController {
	static AppointmentController appointmentController = new AppointmentController();
	static RegistrationController registrationController = new RegistrationController();
	static List<Registration> registrations = new ArrayList<>();
	private static UUID id;
	private static UUID id2;

	@BeforeAll
	public static void setUp() throws SQLException {
		RegistrationDAO registrationDAO = RegistrationDAO.getInstance();
		Registration registration = new Registration("111111111111", "555555555555",
				UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac120002"), null, LocalDate.parse("2022-10-08"),
				LocalDate.parse("2022-10-10"));
		Registration registration2 = new Registration("111111111111", "555555555555",
				UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac120002"), null, LocalDate.parse("2022-10-08"),
				LocalDate.parse("2022-10-10"));
		registrationDAO.insert(registration);
		registrationDAO.insert(registration2);
		id = registration.getId();
		id2 = registration2.getId();
		for (int i = 0; i <= 61; i++) {
			registrations.add(registrationController.createRegistration("111111111111", "555555555555",
					UUID.fromString("3AAAAAAA-BBBB-CCCC-DDDD-2EEEEEEEEEEE"), null, LocalDate.now().plusDays(1),
					LocalDate.now().plusDays(2)));
		}
	}

	@Test
	void testSuccessfullyDeletedAppointment() throws SQLException {
		Appointment appointment = new Appointment(id, LocalDate.now());
		List<Appointment> appointments = new ArrayList<>();
		boolean validID = false;
		appointments.add(appointment);
		appointmentController.insertManyAppointments(appointments);
		for (int i = 0; i < appointments.size(); i++) {
			System.out.println(appointments.get(i).getRegistrationId().toString());
		}
		try {
			appointmentController.deleteAppointment(id);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		appointments = appointmentController.getAllAppointments();
		for (int i = 0; i < appointments.size(); i++) {
			if (appointments.get(i).getRegistrationId().toString() == "e087e6ae-d034-11ec-9d64-0242ac120002") {
				validID = true;
			}
		}
		assertFalse(validID);
	}

	@Test
	void testInvalidRegistrationId() throws SQLException {
		Assertions.assertThrows(EntityNotFoundException.class,
				() -> appointmentController.deleteAppointment(UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac12AAAA")));
	}

	@Test
	void testInsertManyAppointmentsAndGetAll() throws SQLException {
		Appointment appointment1 = new Appointment(id, LocalDate.now());
		Appointment appointment2 = new Appointment(id2, LocalDate.now());
		List<Appointment> appointments = new ArrayList<>();
		appointments.add(appointment1);
		appointments.add(appointment2);
		appointmentController.insertManyAppointments(appointments);
		appointments = appointmentController.getAllAppointments();
		assertTrue(appointments.size() > 0);
	}

	@Test
	void testDrawAppointmentsCorrectAmount() throws SQLException {
		assertEquals(60, appointmentController
				.drawAppointmentsForTomorrow(UUID.fromString("3AAAAAAA-BBBB-CCCC-DDDD-2EEEEEEEEEEE")).size());
	}

	@AfterEach
	public void tearDown() throws SQLException {
		Statement statement = DBConnection.getInstance().getDBconnection().createStatement();
		statement.executeUpdate("DELETE FROM Appointment");
	}

	@AfterAll
	static void cleanUp() throws SQLException {
		Statement statement = DBConnection.getInstance().getDBconnection().createStatement();
		statement.executeUpdate("DELETE FROM Registration");
	}
}