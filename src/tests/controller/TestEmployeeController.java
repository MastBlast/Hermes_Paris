package tests.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import controller.EmployeeController;
import model.Employee;

class TestEmployeeController {
	EmployeeController employeeController = new EmployeeController();

	@Test
	void testGetAllEmployees() {
		List<Employee> employees = null;
		employees = employeeController.getAllEmployees();
		assertNotNull(employees);
	}

	@Test
	void testValidAuthenticate() {
		assertTrue(employeeController.authenticate("Katie", "Brezani"));
	}

	@Test
	void testInvalidPassword() {
		assertFalse(employeeController.authenticate("Matas", "wrongPassword"));
	}

	@Test
	void testInvalidUsername() {
		assertFalse(employeeController.authenticate("Mats", "Kavaliauskas"));
	}

}
