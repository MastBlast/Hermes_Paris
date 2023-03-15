package model;

public class Customer extends Person {
  private String email;

  /**
   * Constructor for Customer.
   * 
   * @param firstName
   * @param lastName
   * @param phoneNumber
   * @param identityNo
   * @param email
   */
  public Customer(String firstName, String lastName, String phoneNumber, String identityNo, String email) {
    super(firstName, lastName, phoneNumber, identityNo);
    this.email = email;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return email;
  }
}
