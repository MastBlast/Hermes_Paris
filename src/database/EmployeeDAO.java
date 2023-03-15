package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import exceptions.EntityNotFoundException;
import model.Employee;
import model.EmployeeRole;

public class EmployeeDAO implements DAOInterface<Employee, String> {
  private static EmployeeDAO instance;

  private final String SELECT_ALL_EMPLOYEES = """
      SELECT Person.firstName, Person.lastName, Person.phoneNo, Person.identityNo, Employee.username, Employee.password, Employee.role, Employee.storeId FROM Person
      JOIN Employee ON Person.identityNo = Employee.identityNo""";

  /**
   * Constructor for EmployeeDAO.
   */
  private EmployeeDAO() {
  }

  /**
   * Returns the singleton instance of EmployeeDAO.
   * 
   * @return the singleton instance of EmployeeDAO
   */
  public static EmployeeDAO getInstance() {
    if (instance == null) {
      instance = new EmployeeDAO();
    }
    return instance;
  }

  /**
   * Inserts a new employee into the database.
   * 
   * @param employee the employee to be inserted
   * @return the inserted employee
   */
  @Override
  public void insert(Employee employee) {
    throw new UnsupportedOperationException("Method not implemented");
  }

  /**
   * Deletes an employee from the database.
   * 
   * @param employee the employee to be deleted
   * @throws EntityNotFoundException
   */
  @Override
  public void delete(String identityNo) throws EntityNotFoundException {
    throw new UnsupportedOperationException("Method not implemented");
  }

  /**
   * Updates an employee in the database.
   * 
   * @param employee the employee to be updated
   * @throws EntityNotFoundException
   */
  @Override
  public void update(Employee employee) throws EntityNotFoundException {
    throw new UnsupportedOperationException("Method not implemented");
  }

  /**
   * Gets an employee from the database.
   * 
   * @param identityNo the identity number of the employee to be retrieved
   * @return the employee with the given identity number
   * @throws EntityNotFoundException
   */
  @Override
  public Employee get(String identityNo) throws EntityNotFoundException {
    throw new UnsupportedOperationException("Method not implemented");
  }

  /**
   * Gets all employees from the database.
   * 
   * @return a list of all employees
   */
  @Override
  public List<Employee> getAll() {
    List<Employee> employees = new ArrayList<>();
    Connection connection = DBConnection.getInstance().getDBconnection();
    try {
      PreparedStatement ps = connection.prepareStatement(SELECT_ALL_EMPLOYEES, Statement.RETURN_GENERATED_KEYS);

      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        employees.add(buildObject(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return employees;
  }

  /**
   * Builds an employee object from a result set.
   * 
   * @param rs the result set
   * @return the employee
   * @throws SQLException
   */
  private Employee buildObject(ResultSet rs) throws SQLException {
    Employee employee = new Employee(rs.getString("firstName"), rs.getString("lastName"), rs.getString("phoneNo"),
        rs.getString("identityNo"), rs.getString("username"), rs.getString("password"),
        EmployeeRole.valueOf(rs.getString("role")), UUID.fromString(rs.getString("storeId")));
    return employee;
  }
}
