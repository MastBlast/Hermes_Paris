package model;

import java.util.UUID;

public class Session {
  private static Employee loggedInEmployee;

  /**
   * Delete the current session.
   */
  public static void delete() {
    loggedInEmployee = null;
  }

  /**
   * Returns the logged in employee.
   *
   * @return the logged in employee
   */
  public static Employee getLoggedInEmployee() {
    return loggedInEmployee;
  }

  /**
   * Sets the logged in employee.
   *
   * @param loggedInEmployee the logged in employee
   */
  public static void setLoggedInEmployee(Employee loggedInEmployee) {
    Session.loggedInEmployee = loggedInEmployee;
  }

  /**
   * Gets employee store id
   *
   * @return employee store id
   */
  public static UUID getEmployeeStoreId() {
    return loggedInEmployee.getStoreId();
  }

  /**
   * Checks if employee is a manager
   *
   * @return true if the employee is a manager
   */
  public static boolean isEmployeeManager() {
    if (loggedInEmployee != null) {
      return loggedInEmployee.getRole().equals(EmployeeRole.Manager);
    }
    return false;
  }
}
