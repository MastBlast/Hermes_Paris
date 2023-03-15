package tests.model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.Customer;

public class TestCustomer {
	private static Customer customer;

	@BeforeAll
	public static void setUp() {
		customer = new Customer("John", "Doe", "12345", "12345", "email@email");
	}

	@Test
	public void testIdentityNo() {
		String identityNo = "1234567890123";
		customer.setIdentityNo(identityNo);
		assertEquals(identityNo, customer.getIdentityNo());
	}

	@Test
	public void testFirstName() {
		String firstName = "John2";
		customer.setFirstName(firstName);
		assertEquals(firstName, customer.getFirstName());
	}

	@Test
	public void testLastName() {
		String lastName = "Doe2";
		customer.setLastName(lastName);
		assertEquals(lastName, customer.getLastName());
	}

	@Test
	public void testPhoneNumber() {
		String phoneNumber = "123456789";
		customer.setPhoneNumber(phoneNumber);
		assertEquals(phoneNumber, customer.getPhoneNumber());
	}

	@Test
	public void testEmail() {
		String email = "email2@email";
		customer.setEmail(email);
		assertEquals(email, customer.getEmail());
	}
}
