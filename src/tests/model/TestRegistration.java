package tests.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.Registration;

public class TestRegistration {
  private static Registration registration;

  @BeforeAll
  public static void setUp() {
    List<String> productBarcodes = new ArrayList<String>();
    productBarcodes.add("123456789");
    productBarcodes.add("123456789");

    registration = new Registration("123456789", "123456789", UUID.randomUUID(), productBarcodes, LocalDate.now(),
        LocalDate.now());
  }

  @Test
  public void testSetAndGetId() {
    UUID id = UUID.randomUUID();
    registration.setId(id);

    assertEquals(id, registration.getId());
  }

  @Test
  public void testSetAndGetCustomerIdentityNo() {
    String customerIdentityNo = "123456789";
    registration.setCustomerIdentityNo(customerIdentityNo);

    assertEquals(customerIdentityNo, registration.getCustomerIdentityNo());
  }

  @Test
  public void testSetAndGetEmployeeIdentityNo() {
    String employeeIdentityNo = "123456789";
    registration.setEmployeeIdentityNo(employeeIdentityNo);

    assertEquals(employeeIdentityNo, registration.getEmployeeIdentityNo());
  }

  @Test
  public void testSetAndGetStoreId() {
    UUID storeId = UUID.randomUUID();
    registration.setStoreId(storeId);

    assertEquals(storeId, registration.getStoreId());
  }

  @Test
  public void testAddProductBarcode() {
    String productBarcode = "123456789";
    int initialSize = registration.getProductBarcodes().size();

    registration.addProductBarcode(productBarcode);

    assertEquals(initialSize + 1, registration.getProductBarcodes().size());
  }

  @Test
  public void testRemoveProductBarcode() {
    String productBarcode = "123456789";
    int initialSize = registration.getProductBarcodes().size();

    registration.removeProductBarcode(productBarcode);

    assertEquals(initialSize - 1, registration.getProductBarcodes().size());
  }

  @Test
  public void testSetAndGetStartDate() {
    LocalDate startDate = LocalDate.now();
    registration.setStartDate(startDate);

    assertEquals(startDate, registration.getStartDate());
  }

  @Test
  public void testSetAndGetEndDate() {
    LocalDate endDate = LocalDate.now();
    registration.setEndDate(endDate);

    assertEquals(endDate, registration.getEndDate());
  }

  @AfterAll
  public static void tearDown() {
    registration = null;
  }
}
