package tests.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.Appointment;

class TestAppointment {
	private static Appointment appointment;

	  @BeforeAll
	  public static void setUp() {
	    appointment = new Appointment(UUID.randomUUID(), LocalDate.now());
	  }

	  @Test
	  public void testSetAndGetId() {
	    UUID id = UUID.randomUUID();
	    appointment.setRegistrationId(id);

	    assertEquals(id, appointment.getRegistrationId());
	  }


	  @Test
	  public void testSetAndGetStartDate() {
	    LocalDate date = LocalDate.now();
	    appointment.setDate(date);

	    assertEquals(date, appointment.getDate());
	  }

	  @AfterAll
	  public static void tearDown() {
	    appointment = null;
	  }

}
