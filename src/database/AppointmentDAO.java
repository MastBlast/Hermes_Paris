package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import exceptions.EntityNotFoundException;
import model.Appointment;

public class AppointmentDAO implements DAOInterface<Appointment, UUID> {
  private static AppointmentDAO instance;

  private final String INSERT_APPOINTMENT = "INSERT INTO Appointment ([date], appointmentId) VALUES (?,?)";
  private final String SELECT_ALL_APPOINTMENTS = "SELECT * FROM Appointment";
  private final String DELETE_APPOINTMENT_BY_ID = "DELETE FROM Appointment WHERE appointmentId = ? ";

  /**
   * Constructor for the AppointmentDAO.
   */
  private AppointmentDAO() {
  }

  /**
   * Returns the instance of the AppointmentDAO.
   * 
   * @return the instance of the AppointmentDAO
   */
  public static AppointmentDAO getInstance() {
    if (instance == null) {
      instance = new AppointmentDAO();
    }
    return instance;
  }

  /**
   * Inserts an appointment into the database.
   * 
   * @param appointment the appointment to be inserted
   */
  @Override
  public void insert(Appointment appointment) {
    throw new UnsupportedOperationException("Method not implemented");
  }

  /**
   * Deletes an appointment from the database.
   * 
   * @param id the id of the appointment to be deleted
   * @throws EntityNotFoundException
   */
  @Override
  public void delete(UUID id) throws EntityNotFoundException {
    Connection connection = DBConnection.getInstance().getDBconnection();
    try {
      PreparedStatement ps = connection.prepareStatement(DELETE_APPOINTMENT_BY_ID, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, id.toString());

      int result = ps.executeUpdate();
      if (result == 0) {
        throw new EntityNotFoundException("Appointment with id " + id + " not found");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Updates an appointment in the database.
   *
   * @param appointment the appointment to be updated
   * @throws EntityNotFoundException
   */
  @Override
  public void update(Appointment appointment) throws EntityNotFoundException {
    throw new UnsupportedOperationException("Method not implemented");
  }

  /**
   * Returns an appointment from the database.
   * 
   * @param id the id of the appointment to be returned
   * @return the appointment with the given id
   * @throws EntityNotFoundException
   */
  @Override
  public Appointment get(UUID id) throws EntityNotFoundException {
    throw new UnsupportedOperationException("Method not implemented");
  }

  /**
   * Returns all appointments from the database.
   * 
   * @return a list of all appointments
   */
  @Override
  public List<Appointment> getAll() {
    List<Appointment> appointments = new ArrayList<>();
    Connection connection = DBConnection.getInstance().getDBconnection();
    try {
      PreparedStatement ps = connection.prepareStatement(SELECT_ALL_APPOINTMENTS, Statement.RETURN_GENERATED_KEYS);

      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        appointments.add(buildObject(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return appointments;
  }

  /**
   * Inserts many appointments into the database.
   *
   * @param appointments the appointments to be inserted
   * @throws SQLException
   */
  public void insertMany(List<Appointment> appointments) throws SQLException {
    Connection connection = DBConnection.getInstance().getDBconnection();
    try (PreparedStatement statement = connection.prepareStatement(INSERT_APPOINTMENT)) {

      int i = 0;
      for (Appointment appointment : appointments) {
        statement.setString(1, appointment.getDate().toString());
        statement.setString(2, appointment.getRegistrationId().toString());
        statement.addBatch();
        i++;
      }
      if (i == appointments.size()) {
        statement.executeBatch();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * Builds an appointment from a result set.
   * 
   * @param rs the result set
   * @return the appointment
   * @throws SQLException
   */
  private Appointment buildObject(ResultSet rs) throws SQLException {
    Appointment appointment = new Appointment(UUID.fromString(rs.getString("appointmentId")),
        LocalDate.parse(rs.getString("date")));
    return appointment;
  }
}
