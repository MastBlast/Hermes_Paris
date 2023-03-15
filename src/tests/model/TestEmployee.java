package tests.model;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.Employee;
import model.EmployeeRole;

class TestEmployee {
	private static Employee employee;

	@BeforeAll
	public static void setUp() {
		employee = new Employee("Aymeric", "Bourdin", "+45123456", "111111111111", "gabriel", "Passw0rd",
				EmployeeRole.Manager, UUID.fromString("3AAAAAAA-BBBB-CCCC-DDDD-2EEEEEEEEEEE"));
	}

	@Test
	public void testGetAndSetUsername() {
		String username = "baymeric";
		employee.setUsername(username);
		assertEquals(username, employee.getUsername());
	}

	@Test
	public void testGetAndSetPassword() {
		String password = "hermesfat64";
		employee.setPassword(password);
		assertEquals(password, employee.getPassword());
	}

	@Test
	public void testGetAndSetRole() {
		employee.setRole(EmployeeRole.StaffMember);
		assertEquals(EmployeeRole.StaffMember, employee.getRole());
	}

	@Test
	public void testGetAndSetStoreId() {
		UUID storeId = UUID.fromString("3BBBBBBA-BBBB-CCCC-DDDD-2EEEEEEEEEEE");
		employee.setStoreId(storeId);
		assertEquals(storeId, employee.getStoreId());
	}

}
