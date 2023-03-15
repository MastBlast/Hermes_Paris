package tests.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import controller.ProductController;
import exceptions.EntityNotFoundException;
import model.Product;

class TestProductController {
	ProductController productController = new ProductController();

	@Test
	void testGetProduct() throws EntityNotFoundException {
		Product product = null;
		product = productController.getProduct("155144133");
		assertEquals(product.getBarcode(), ("155144133"));
	}

	@Test
	void testGetAllCustomers() throws SQLException {
		List<Product> products = null;
		products = productController.getAllProducts();
		assertNotNull(products);
	}

}
