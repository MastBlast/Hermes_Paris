package model;

import java.util.UUID;

public class Store {
  private UUID id;
  private String address;
  private String zipCode;
  private int customerCapacity;

  /**
   * Constructor for Store with auto-generated id.
   * 
   * @param address
   * @param zipCode
   * @param customerCapacity
   */
  public Store(String address, String zipCode, int customerCapacity) {
    this(UUID.randomUUID(), address, zipCode, customerCapacity);
  }

  /**
   * Constructor for Store with custom id.
   *
   * @param id
   * @param address
   * @param zipCode
   * @param customerCapacity
   */
  public Store(UUID id, String address, String zipCode, int customerCapacity) {
    super();
    this.id = id;
    this.address = address;
    this.zipCode = zipCode;
    this.customerCapacity = customerCapacity;
  }

  /**
   * @return the id
   */
  public UUID getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(UUID id) {
    this.id = id;
  }

  /**
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * @param address the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * @return the zipCode
   */
  public String getZipCode() {
    return zipCode;
  }

  /**
   * @param zipCode the zipCode to set
   */
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  /**
   * @return the customerCapacity
   */
  public int getCustomerCapacity() {
    return customerCapacity;
  }

  /**
   * @param customerCapacity the customerCapacity to set
   */
  public void setCustomerCapacity(int customerCapacity) {
    this.customerCapacity = customerCapacity;
  }
}
