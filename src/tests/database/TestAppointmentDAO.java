package tests.database;

import static org.junit.jupiter.api.Assertions.*;

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
import database.AppointmentDAO;
import exceptions.EntityNotFoundException;
import model.Appointment;
import model.Registration;

class TestAppointmentDAO {
	private static UUID id;
	private static UUID id2;


	@BeforeAll
	  public static void setUp() throws SQLException {
		RegistrationDAO registrationDAO = RegistrationDAO.getInstance();
	    Registration registration = new Registration("111111111111", "555555555555",
	        UUID.fromString("3aaaaaaa-bbbb-cccc-dddd-2eeeeeeeeeee"), null, LocalDate.parse("2022-05-08"),
	        LocalDate.parse("2022-05-10"));
	    Registration registration2 = new Registration("111111111111", "555555555555",
		        UUID.fromString("3aaaaaaa-bbbb-cccc-dddd-2eeeeeeeeeee"), null, LocalDate.parse("2022-05-08"),
		        LocalDate.parse("2022-05-10"));
	    registrationDAO.insert(registration);
	    registrationDAO.insert(registration2);
	    id = registration.getId();
	    id2 = registration2.getId();
	  }

	  @AfterAll
	  public static void tearDown() {
	    try {
	      Statement statement = DBConnection.getInstance().getDBconnection().createStatement();
	      //statement.executeUpdate("DELETE FROM Appointment");
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	  }

	  @Test
	  public void testInsertManyAppointments() {
	    AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();
	    Appointment appointment = new Appointment(id2, LocalDate.now());
	    List<Appointment> appointments = new ArrayList<>();
	    appointments.add(appointment);
	    List<Appointment> appointmentsFromDBBefore = appointmentDAO.getAll();
	    try {
			appointmentDAO.insertMany(appointments);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        List<Appointment> appointmentsFromDBAfter = appointmentDAO.getAll();
        assertTrue(appointments.size() == (appointmentsFromDBAfter.size() - appointmentsFromDBBefore.size()));
	    
	  }

	  @Test
	  public void testDeleteAppointment() {
		  AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();
   	      Appointment appointment = new Appointment(id, LocalDate.now());
	      List<Appointment> appointments = new ArrayList<>();
	      appointments.add(appointment);
		  List<Appointment> appointmentsFromDBBefore = appointmentDAO.getAll();
	      try {
			appointmentDAO.delete(appointment.getRegistrationId());
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    List<Appointment> appointmentsFromDBAfter = appointmentDAO.getAll();
        assertTrue(1 == (appointmentsFromDBBefore.size() - appointmentsFromDBAfter.size()));
	  }

	 
	  @Test
	  public void testGetAllAppointments() {
		AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();
   	    Appointment appointment = new Appointment(id, LocalDate.now());

	    try {
	      Statement statement = DBConnection.getInstance().getDBconnection().createStatement();
	      statement.executeUpdate("DELETE FROM appointment");
	    } catch (SQLException e) {
	      assertTrue(false);
	    }

	    List<Appointment> appointments = new ArrayList<>();
	    appointments.add(appointment);
	    
	    try {
			appointmentDAO.insertMany(appointments);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println(appointmentDAO.getAll().size());
	    assertTrue(appointmentDAO.getAll().size() == 1);
	  }

}
