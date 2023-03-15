package tests.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import controller.CustomerController;
import model.Customer;

class TestCustomerController {
	CustomerController customerController = new CustomerController();

	@Test
	void testGetAllCustomers() {
		List<Customer> customers = null;
		customers = customerController.getAllCustomers();
		assertEquals(customers.get(0).getIdentityNo(), "111111111111");
	}
}
