package controller;

import java.util.List;

import database.EmployeeDAO;
import model.Employee;
import model.Session;

public class EmployeeController {
  /**
   * Get all the employees
   * 
   * @return a list of employees
   */
  public List<Employee> getAllEmployees() {
    return EmployeeDAO.getInstance().getAll();
  }

  /**
   * Authenticates the employee.
   * 
   * @param employee
   * @return true if the employee exists in the database, false otherwise
   */
  public boolean authenticate(String username, String password) {
    List<Employee> employees = getAllEmployees();

    for (Employee employee : employees) {
      if (employee.getUsername().equals(username) && employee.getPassword().equals(password)) {
        Session.setLoggedInEmployee(employee);
        return true;
      }
    }

    return false;
  }
}
