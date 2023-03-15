package tests.model;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.Store;

public class TestStore {
  private static Store store;

  @BeforeAll
  public static void setUp() {
    store = new Store("123 Main St", "123", 100);
  }

  @Test
  public void testId() {
    UUID id = UUID.randomUUID();
    store.setId(id);

    assertEquals(id, store.getId());
  }

  @Test
  public void testAddress() {
    String address = "12345 Main St";
    store.setAddress(address);

    assertEquals(address, store.getAddress());
  }

  @Test
  public void testZipCode() {
    String zipCode = "12345";
    store.setZipCode(zipCode);

    assertEquals(zipCode, store.getZipCode());
  }

  @Test
  public void testCustomerCapacity() {
    int customerCapacity = 200;
    store.setCustomerCapacity(customerCapacity);

    assertEquals(customerCapacity, store.getCustomerCapacity());
  }

  @AfterAll
  public static void tearDown() {
    store = null;
  }
}
