package model;

import java.util.UUID;

public class Employee extends Person {
  private String username;
  private String password;
  private EmployeeRole role;
  private UUID storeId;

  /**
   * Constructor for Employee.
   * 
   * @param firstName
   * @param lastName
   * @param phoneNumber
   * @param identityNo
   * @param username
   * @param password
   * @param role
   * @param storeId
   */
  public Employee(String firstName, String lastName, String phoneNumber, String identityNo, String username,
      String password, EmployeeRole role, UUID storeId) {
    super(firstName, lastName, phoneNumber, identityNo);
    this.username = username;
    this.password = password;
    this.role = role;
    this.storeId = storeId;
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the role
   */
  public EmployeeRole getRole() {
    return role;
  }

  /**
   * @param role the role to set
   */
  public void setRole(EmployeeRole role) {
    this.role = role;
  }

  /**
   * @return the storeId
   */
  public UUID getStoreId() {
    return storeId;
  }

  /**
   * @param storeId the storeId to set
   */
  public void setStoreId(UUID storeId) {
    this.storeId = storeId;
  }

  @Override
  public String toString() {
    return getUsername();
  }
}
