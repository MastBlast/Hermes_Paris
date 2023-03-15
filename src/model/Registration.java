package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Registration {
  private UUID id;
  private String customerIdentityNo;
  private String employeeIdentityNo;
  private UUID storeId;
  private List<String> productBarcodes = new ArrayList<String>();
  private LocalDate startDate;
  private LocalDate endDate;

  /**
   * Constructor for Registration with auto-generated id.
   *
   * @param customerIdentityNo
   * @param employeeIdentityNo
   * @param storeId
   * @param productBarcodes
   * @param startDate
   * @param endDate
   */
  public Registration(String customerIdentityNo, String employeeIdentityNo, UUID storeId, List<String> productBarcodes,
      LocalDate startDate, LocalDate endDate) {
    this(UUID.randomUUID(), customerIdentityNo, employeeIdentityNo, storeId, productBarcodes, startDate, endDate);
  }

  /**
   * Constructor for Registration with custom id.
   *
   * @param id
   * @param customerIdentityNo
   * @param employeeIdentityNo
   * @param storeId
   * @param productBarcodes
   * @param startDate
   * @param endDate
   */
  public Registration(UUID id, String customerIdentityNo, String employeeIdentityNo, UUID storeId,
      List<String> productBarcodes, LocalDate startDate, LocalDate endDate) {
    this.id = id;
    this.customerIdentityNo = customerIdentityNo;
    this.employeeIdentityNo = employeeIdentityNo;
    this.storeId = storeId;
    this.productBarcodes = productBarcodes;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  /**
   * @param productBarcode the productBarcode to add
   */
  public void addProductBarcode(String productBarcode) {
    this.productBarcodes.add(productBarcode);
  }

  /**
   * @param productBarcode the productBarcode to remove
   */
  public void removeProductBarcode(String productBarcode) {
    this.productBarcodes.remove(productBarcode);
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
   * @return the customerIdentityNo
   */
  public String getCustomerIdentityNo() {
    return customerIdentityNo;
  }

  /**
   * @param customerIdentityNo the customerIdentityNo to set
   */
  public void setCustomerIdentityNo(String customerIdentityNo) {
    this.customerIdentityNo = customerIdentityNo;
  }

  /**
   * @return the employeeIdentityNo
   */
  public String getEmployeeIdentityNo() {
    return employeeIdentityNo;
  }

  /**
   * @param employeeIdentityNo the employeeIdentityNo to set
   */
  public void setEmployeeIdentityNo(String employeeIdentityNo) {
    this.employeeIdentityNo = employeeIdentityNo;
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

  /**
   * @return the productBarcodes
   */
  public List<String> getProductBarcodes() {
    return productBarcodes;
  }

  /**
   * @param productBarcodes the productBarcodes to set
   */
  public void setProductBarcodes(List<String> productBarcodes) {
    this.productBarcodes = productBarcodes;
  }

  /**
   * @return the startDate
   */
  public LocalDate getStartDate() {
    return startDate;
  }

  /**
   * @param startDate the startDate to set
   */
  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  /**
   * @return the endDate
   */
  public LocalDate getEndDate() {
    return endDate;
  }

  /**
   * @param endDate the endDate to set
   */
  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }
}
