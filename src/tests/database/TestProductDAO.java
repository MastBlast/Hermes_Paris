package tests.database;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import database.ProductDAO;
import exceptions.EntityNotFoundException;
import model.Product;

class TestProductDAO {
	private static ProductDAO productDao;
	
	@BeforeAll
	  public static void setUp() {
		productDao = ProductDAO.getInstance();	
	}
	

	@Test
	void testGetProductByBarcode() {
		Product product = null;
		try {
			product = productDao.get("155144133");
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(product.getName());
		assertTrue(product != null);
	}

}
