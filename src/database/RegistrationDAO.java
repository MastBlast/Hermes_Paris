package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import exceptions.EntityNotFoundException;
import model.Registration;

public class RegistrationDAO implements DAOInterface<Registration, UUID> {
  private static RegistrationDAO instance;

  private final String INSERT_REGISTRATION = "INSERT INTO Registration ([id], customerIdentityNo, storeId, startDate, endDate, employeeIdentityNo) VALUES (?,?,?,?,?,?)";
  private final String INSERT_PRODUCTS = "INSERT INTO Registration_Product (registrationId, productBarcode) VALUES (?,?)";
  private final String DELETE_REGISTRATION_BY_ID = "DELETE FROM Registration WHERE [id] = ? ";
  private final String DELETE_PRODUCT_BY_REGISTRATION_ID = "DELETE FROM Registration_Product WHERE registrationId = ? ";
  private final String FIND_REGISTRATION_BY_ID = "SELECT * FROM Registration WHERE [id] = ?";
  private final String FIND_PRODUCT_BY_REGISTRATION_ID = "SELECT * FROM Registration_Product WHERE registrationId = ?";
  private final String SELECT_ALL_REGISTRATIONS = "SELECT * FROM Registration";
  private final String SELECT_REGISTRATIONS_FOR_DATE = "SELECT * FROM Registration WHERE startDate <= ? AND endDate >= ?";
  private final String UPDATE_REGISTRATION_BY_ID = """
      UPDATE Registration
      SET customerIdentityNo = ?, storeId = ?, startDate = ?, endDate = ?, employeeIdentityNo = ?
      WHERE [id] = ? """;

  /**
   * Constructor for RegistrationDAO.
   */
  private RegistrationDAO() {
  }

  /**
   * Returns the single instance of RegistrationDAO.
   *
   * @return the single instance of RegistrationDAO
   */
  public static RegistrationDAO getInstance() {
    if (instance == null) {
      instance = new RegistrationDAO();
    }
    return instance;
  }

  /**
   * Inserts a new registration into the database.
   *
   * @param registration the registration to insert
   * @throws SQLException
   */
  @Override
  public void insert(Registration registration) throws SQLException {
    Connection connection = DBConnection.getInstance().getDBconnection();
    connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
    try (PreparedStatement ps = connection.prepareStatement(INSERT_REGISTRATION, Statement.RETURN_GENERATED_KEYS);
        PreparedStatement ps2 = connection.prepareStatement(INSERT_PRODUCTS, Statement.RETURN_GENERATED_KEYS);) {
      connection.setAutoCommit(false);

      ps.setString(1, registration.getId().toString());
      ps.setString(2, registration.getCustomerIdentityNo());
      ps.setString(3, registration.getStoreId().toString());
      ps.setString(4, Date.valueOf(registration.getStartDate()).toString());
      ps.setString(5, Date.valueOf(registration.getEndDate()).toString());
      ps.setString(6, registration.getEmployeeIdentityNo());

      ps.executeUpdate();

      if (registration.getProductBarcodes() != null) {
        for (String productBarcode : registration.getProductBarcodes()) {
          ps2.setString(1, registration.getId().toString());
          ps2.setString(2, productBarcode);
          ps2.addBatch();
        }
        ps2.executeBatch();
      }
      connection.commit();

    } catch (SQLException e) {
      connection.rollback();
    } finally {
      connection.setAutoCommit(true);

    }
  }

  /**
   * Deletes a registration from the database.
   *
   * @param id the id of the registration to delete
   * @throws EntityNotFoundException, SQLException
   */
  @Override
  public void delete(UUID id) throws EntityNotFoundException, SQLException {
    Connection connection = DBConnection.getInstance().getDBconnection();
    connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
    try {
      connection.setAutoCommit(false);
      PreparedStatement ps = connection.prepareStatement(DELETE_REGISTRATION_BY_ID, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, id.toString());

      int result = ps.executeUpdate();
      PreparedStatement ps2 = connection.prepareStatement(DELETE_PRODUCT_BY_REGISTRATION_ID,
          Statement.RETURN_GENERATED_KEYS);

      ps2.setString(1, id.toString());
      connection.commit();
      if (result == 0) {
        throw new EntityNotFoundException("Registration with id " + id + " not found");
      }
    } catch (SQLException e) {
      connection.rollback();
    } finally {
      connection.setAutoCommit(true);
    }
  }

  /**
   * Updates a registration in the database.
   *
   * @param registration the registration to update
   * @throws EntityNotFoundException, SQLException
   */
  @Override
  public void update(Registration registration) throws EntityNotFoundException, SQLException {
    Connection connection = DBConnection.getInstance().getDBconnection();
    connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
    try {
      connection.setAutoCommit(false);
      PreparedStatement ps = connection.prepareStatement(UPDATE_REGISTRATION_BY_ID, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, registration.getCustomerIdentityNo());
      ps.setString(2, registration.getStoreId().toString());
      ps.setString(3, Date.valueOf(registration.getStartDate()).toString());
      ps.setString(4, Date.valueOf(registration.getEndDate()).toString());
      ps.setString(5, registration.getEmployeeIdentityNo());
      ps.setString(6, registration.getId().toString());
      int result = ps.executeUpdate();

      PreparedStatement ps2 = connection.prepareStatement(DELETE_PRODUCT_BY_REGISTRATION_ID,
          Statement.RETURN_GENERATED_KEYS);
      ps2.setString(1, registration.getId().toString());
      ps2.executeUpdate();

      PreparedStatement ps3 = connection.prepareStatement(INSERT_PRODUCTS, Statement.RETURN_GENERATED_KEYS);

      if (registration.getProductBarcodes() != null) {
        for (String productBarcode : registration.getProductBarcodes()) {
          ps3.setString(1, registration.getId().toString());
          ps3.setString(2, productBarcode);
          ps3.addBatch();
        }
        ps3.executeBatch();
      }
      connection.commit();

      if (result == 0) {
        throw new EntityNotFoundException("Registration with id " + registration.getId() + " not found");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      connection.rollback();
    } finally {
      connection.setAutoCommit(true);

    }
  }

  /**
   * Returns a registration from the database.
   *
   * @param id the id of the registration to return
   * @return the registration with the given id
   * @throws EntityNotFoundException, SQLException
   */
  @Override
  public Registration get(UUID id) throws EntityNotFoundException, SQLException {
    Connection connection = DBConnection.getInstance().getDBconnection();
    connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
    Registration registration = null;
    try {
      connection.setAutoCommit(false);
      PreparedStatement ps = connection.prepareStatement(FIND_REGISTRATION_BY_ID, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, id.toString());

      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        String registrationId = rs.getString("id");
        PreparedStatement ps2 = connection.prepareStatement(FIND_PRODUCT_BY_REGISTRATION_ID,
            Statement.RETURN_GENERATED_KEYS);
        ps2.setString(1, registrationId);

        ResultSet rs2 = ps2.executeQuery();
        registration = buildObject(rs, rs2);
      }
      connection.commit();

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      connection.setAutoCommit(true);
    }

    if (registration == null) {
      throw new EntityNotFoundException("Registration with id " + id + " not found");
    }

    return registration;
  }

  /**
   * Returns all registrations from the database.
   *
   * @return all registrations
   * @throws SQLException
   */
  @Override
  public List<Registration> getAll() throws SQLException {
    List<Registration> registrations = new ArrayList<>();
    Connection connection = DBConnection.getInstance().getDBconnection();
    try {
      PreparedStatement ps = connection.prepareStatement(SELECT_ALL_REGISTRATIONS, Statement.RETURN_GENERATED_KEYS);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        registrations.add(buildObject(rs, null));
      }
      connection.commit();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return registrations;
  }

  /**
   * Returns all registrations from the database for a given date
   *
   * @return all registrations for a given date
   * @param date the date to search for
   * @throws SQLException
   */
  public List<Registration> getRegistrationsForDate(LocalDate date) throws SQLException {
    List<Registration> registrations = new ArrayList<>();
    Connection connection = DBConnection.getInstance().getDBconnection();
    try {
      PreparedStatement ps = connection.prepareStatement(SELECT_REGISTRATIONS_FOR_DATE,
          Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, Date.valueOf(date).toString());
      ps.setString(2, Date.valueOf(date).toString());

      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        registrations.add(buildObject(rs, null));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return registrations;
  }

  /**
   * Builds a registration object from a result set.
   *
   * @param rs        the result set
   * @param rsProduct the result set for the products
   * @return the registration
   * @throws SQLException
   */
  private Registration buildObject(ResultSet rs, ResultSet rsProduct) throws SQLException {
    List<String> products = new ArrayList<>();
    if (rsProduct != null) {
      while (rsProduct.next()) {
        products.add(rsProduct.getString("productBarcode"));
      }
    }
    Registration registration = new Registration(UUID.fromString(rs.getString("id")),
        rs.getString("customerIdentityNo"), rs.getString("employeeIdentityNo"),
        UUID.fromString(rs.getString("storeId")), products, LocalDate.parse(rs.getString("startDate")),
        LocalDate.parse(rs.getString("endDate")));
    return registration;
  }
}
