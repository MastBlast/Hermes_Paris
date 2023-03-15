package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import database.RegistrationDAO;
import exceptions.EntityNotFoundException;
import model.Registration;

public class RegistrationController {
  /**
   * Creates a new registration.
   * 
   * @param customerIdentityNo
   * @param employeeIdentityNo
   * @param storeId
   * @param productBarcodes
   * @param startDate
   * @param endDate
   * @return the created registration
   * @throws IllegalArgumentException, SQLException
   */
  public Registration createRegistration(String customerIdentityNo, String employeeIdentityNo, UUID storeId,
      List<String> productBarcodes, LocalDate startDate, LocalDate endDate)
      throws IllegalArgumentException, SQLException {
    Registration registration = new Registration(customerIdentityNo, employeeIdentityNo, storeId, productBarcodes,
        startDate, endDate);
    validateRegistration(registration);
    RegistrationDAO.getInstance().insert(registration);
    return registration;
  }

  /**
   * Updates the registration.
   * 
   * @param id
   * @param customerIdentityNo
   * @param employeeIdentityNo
   * @param storeId
   * @param productBarcodes
   * @param startDate
   * @param endDate
   * @return the updated registration
   * @throws EntityNotFoundException, IllegalArgumentException, SQLException
   */
  public Registration updateRegistration(UUID id, String customerIdentityNo, String employeeIdentityNo, UUID storeId,
      List<String> productBarcodes, LocalDate startDate, LocalDate endDate)
      throws EntityNotFoundException, IllegalArgumentException, SQLException {
    Registration registration = new Registration(id, customerIdentityNo, employeeIdentityNo, storeId, productBarcodes,
        startDate, endDate);
    validateRegistration(registration);
    RegistrationDAO.getInstance().update(registration);
    return registration;
  }

  /**
   * Deletes the registration.
   * 
   * @param id
   * @throws EntityNotFoundException, SQLException
   */
  public void deleteRegistration(UUID id) throws EntityNotFoundException, SQLException {
    RegistrationDAO.getInstance().delete(id);
  }

  /**
   * Returns the registration with the given id.
   * 
   * @param id
   * @return the registration with the given id
   * @throws EntityNotFoundException, SQLException
   */
  public Registration getRegistration(UUID id) throws EntityNotFoundException, SQLException {
    return RegistrationDAO.getInstance().get(id);
  }

  /**
   * Returns all registrations.
   * 
   * @return all registrations
   * @throws SQLException
   */
  public List<Registration> getAllRegistrations() throws SQLException {
    return RegistrationDAO.getInstance().getAll();
  }

  /**
   * Returns all registrations for given date
   *
   * @param date
   * @return all registrations for given date
   * @throws SQLException
   */
  public List<Registration> getRegistrationsForDate(LocalDate date) throws SQLException {
    return RegistrationDAO.getInstance().getRegistrationsForDate(date);
  }

  /**
   * Validates the given registration.
   *
   * @param registration
   * @throws IllegalArgumentException
   */
  private void validateRegistration(Registration registration) throws IllegalArgumentException {
    if (Utils.isValidIdentityNo(registration.getCustomerIdentityNo()) == false) {
      throw new IllegalArgumentException("Customer identity number is not valid");
    }
    if (Utils.isValidIdentityNo(registration.getEmployeeIdentityNo()) == false) {
      throw new IllegalArgumentException("Employee identity number is not valid");
    }
    if (registration.getStartDate() == null) {
      throw new IllegalArgumentException("Start date is not valid");
    }
    if (!registration.getStartDate().isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Start date must be after today");
    }
    if (registration.getEndDate() == null) {
      throw new IllegalArgumentException("End date is not valid");
    }
    if (!registration.getEndDate().isAfter(registration.getStartDate())) {
      throw new IllegalArgumentException("End date must be after start date");
    }
    if (ChronoUnit.DAYS.between(registration.getStartDate(), registration.getEndDate()) > 7) {
      throw new IllegalArgumentException("Registration must be less than 7 days");
    }

    List<String> productBarcodes = registration.getProductBarcodes();

    if(productBarcodes != null) {
      List<String> uniqueProductBarcodes = productBarcodes.stream().distinct().collect(Collectors.toList());
      if (uniqueProductBarcodes.size() != productBarcodes.size()) {
        throw new IllegalArgumentException("Product barcodes must be unique");
      }
    }
  }

  /**
   * Get all appointments for a given store.
   *
   * @param registrations the registrations to get appointments for
   * @param storeId       the storeId to get appointments for
   * @return all appointments from the database for a given store
   */
  public List<Registration> getRegistrationsForStore(List<Registration> registrations, UUID storeId) {
    List<Registration> storeRegistrations = new ArrayList<>();
    for (Registration r : registrations) {
      if (r.getStoreId().equals(storeId)) {
        storeRegistrations.add(r);
      }
    }
    return storeRegistrations;
  }
}
