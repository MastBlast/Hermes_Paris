package tests.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.Product;

class TestProduct {

	private static Product product;

	@BeforeAll
	public static void setUp() {
		product = new Product("ABC-abc-1234", UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac120002"), "Bag", 56,
				1660.25);
	}

	@Test
	public void testSetAndGetBarcode() {
		String barcode = "ABC-abc-1235";
		product.setBarcode(barcode);

		assertEquals(barcode, product.getBarcode());
	}

	@Test
	public void testSetAndGetStoreId() {
		UUID storeId = UUID.fromString("e087e6ae-d034-11ec-9d64-0242ac120022");
		product.setStoreId(storeId);
		;

		assertEquals(storeId, product.getStoreId());
	}

	@Test
	public void testSetAndGetName() {
		String name = "Fragrance";
		product.setName(name);
		;

		assertEquals(name, product.getName());
	}

	@Test
	public void testSetAndGetStock() {
		int stock = 5878;
		product.setStock(stock);
		assertEquals(stock, product.getStock());
	}

	@Test
	public void testSetAndGetPrice() {
		double price = 5878.25;
		product.setPrice(price);
		assertEquals(price, product.getPrice());
	}

	@AfterAll
	public static void tearDown() {
		product = null;
	}

}
