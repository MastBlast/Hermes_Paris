package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import database.AppointmentDAO;
import database.RegistrationDAO;
import exceptions.EntityNotFoundException;
import model.Appointment;
import model.Registration;
import model.Store;

public class AppointmentController {
  private RegistrationController registrationController;
  private StoreController storeController;

  /**
   * Constructor for AppointmentController.
   */
  public AppointmentController() {
    registrationController = new RegistrationController();
    storeController = new StoreController();
  }

  /**
   * Creates and inserts an appointments into the database.
   *
   * @param appointments the appointments to be inserted
   * @return the appointments that were inserted
   * @throws SQLException
   */
  public List<Appointment> insertManyAppointments(List<Appointment> appointments) throws SQLException {
    AppointmentDAO.getInstance().insertMany(appointments);
    return appointments;
  }

  /**
   * Delete an appointment from the database.
   *
   * @param id the id of the appointment to be deleted
   * @throws EntityNotFoundException
   */
  public void deleteAppointment(UUID id) throws EntityNotFoundException {
    AppointmentDAO.getInstance().delete(id);
    try {
      RegistrationDAO.getInstance().delete(id);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get all appointments from the database.
   *
   * @return all appointments from the database
   */
  public List<Appointment> getAllAppointments() {
    return AppointmentDAO.getInstance().getAll();
  }

  /**
   * Draw appointments for the next day for a store.
   *
   * @param storeId the storeId to get appointments for
   * @return all appointments from the database for a given store
   */
  public List<Appointment> drawAppointmentsForTomorrow(UUID storeId) throws SQLException {
    try {
      Store store = storeController.getStore(storeId);
      int storeCapacity = store.getCustomerCapacity();

      LocalDate tomorrow = LocalDate.now().plusDays(1);
      List<Registration> registrationsForTomorrow = registrationController.getRegistrationsForDate(tomorrow);

      List<Appointment> appointments = getAllAppointments();

      List<Registration> uniqueRegistrationsForTomorrow = new ArrayList<>();
      int alreadyCreatedCount = 0;

      // Filter out registrations that already have appointments
      for (Registration registration : registrationsForTomorrow) {
        boolean wasFound = false;
        for (Appointment appointment : appointments) {
          if (registration.getId().equals(appointment.getRegistrationId())) {
            alreadyCreatedCount++;
            wasFound = true;
            break;
          }
        }
        if (!wasFound) {
          uniqueRegistrationsForTomorrow.add(registration);
        }
      }

      List<Appointment> drawnAppointments = new ArrayList<>();
      List<Registration> storeRegistrations = registrationController .getRegistrationsForStore(uniqueRegistrationsForTomorrow, store.getId());

      // Draw appointments from the store's registrations randomly
      for (int i = 0; i < storeCapacity - alreadyCreatedCount; i++) {
        if (storeRegistrations.size() > 0) {
          int randomIndex = (int) (Math.random() * storeRegistrations.size());
          Appointment appointment = new Appointment(storeRegistrations.get(randomIndex).getId(), tomorrow);
          drawnAppointments.add(appointment);
          storeRegistrations.remove(randomIndex);
        }
      }

      AppointmentDAO.getInstance().insertMany(drawnAppointments);

      System.out.println("---- Draw Appointments for Tomorrow for store " + store.getAddress() + " ----");
      System.out.println("Store: " + store.getId() + " has " + storeRegistrations.size() + " registrations for tomorrow that are not taken, capacity: " + storeCapacity);
      System.out.println("Picked appointments: " + drawnAppointments.size());
      System.out.println("----------------------------------------");

      return drawnAppointments;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}
