package model;

public abstract class Person {
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String identityNo;

  /**
   * Constructor for Person.
   * 
   * @param firstName
   * @param lastName
   * @param phoneNumber
   * @param identityNo
   */
  public Person(String firstName, String lastName, String phoneNumber, String identityNo) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
    this.identityNo = identityNo;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @param phoneNumber the phoneNumber to set
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * @return the identityNo
   */
  public String getIdentityNo() {
    return identityNo;
  }

  /**
   * @param identityNo the identityNo to set
   */
  public void setIdentityNo(String identityNo) {
    this.identityNo = identityNo;
  }
}
