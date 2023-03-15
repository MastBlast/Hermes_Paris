package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exceptions.EntityNotFoundException;
import model.Customer;

public class CustomerDAO implements DAOInterface<Customer, String> {
  private static CustomerDAO instance;

  private final String SELECT_ALL_CUSTOMERS = """
      SELECT Person.firstName, Person.lastName, Person.phoneNo, Person.identityNo, Customer.email FROM Person
      JOIN Customer ON Person.identityNo = Customer.identityNo""";

  /**
   * Constructor for CustomerDAO.
   */
  private CustomerDAO() {
  }

  /**
   * Returns the singleton instance of CustomerDAO.
   *
   * @return the singleton instance of CustomerDAO
   */
  public static CustomerDAO getInstance() {
    if (instance == null) {
      instance = new CustomerDAO();
    }
    return instance;
  }

  /**
   * Inserts a new customer into the database.
   *
   * @param customer the customer to be inserted
   */
  @Override
  public void insert(Customer customer) {
    throw new UnsupportedOperationException("Method not implemented");
  }

  /**
   * Deletes a customer from the database.
   *
   * @param identityNo the identity number of the customer to be deleted
   * @throws EntityNotFoundException
   */
  @Override
  public void delete(String identityNo) throws EntityNotFoundException {
    throw new UnsupportedOperationException("Method not implemented");
  }

  /**
   * Updates a customer in the database.
   *
   * @param customer the customer to be updated
   * @throws EntityNotFoundException
   */
  @Override
  public void update(Customer customer) throws EntityNotFoundException {
    throw new UnsupportedOperationException("Method not implemented");
  }

  /**
   * Returns a customer from the database.
   *
   * @param identityNo the identity number of the customer to be returned
   * @return the customer with the given identity number
   * @throws EntityNotFoundException
   */
  @Override
  public Customer get(String identityNo) throws EntityNotFoundException {
    throw new UnsupportedOperationException("Method not implemented");
  }

  /**
   * Returns all customers from the database.
   *
   * @return a list of all customers
   */
  @Override
  public List<Customer> getAll() {
    List<Customer> customers = new ArrayList<>();
    Connection connection = DBConnection.getInstance().getDBconnection();
    try {
      PreparedStatement ps = connection.prepareStatement(SELECT_ALL_CUSTOMERS, Statement.RETURN_GENERATED_KEYS);

      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        customers.add(buildObject(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return customers;
  }

  /**
   * Builds a customer object from a result set.
   *
   * @param rs the result set
   * @return the customer
   * @throws SQLException
   */
  private Customer buildObject(ResultSet rs) throws SQLException {
    Customer customer = new Customer(rs.getString("firstName"), rs.getString("lastName"), rs.getString("phoneNo"),
        rs.getString("identityNo"), rs.getString("email"));
    return customer;
  }
}
