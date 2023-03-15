package controller;

import java.util.List;

import database.CustomerDAO;
import model.Customer;

public class CustomerController {
  /**
   * Get all customers from the database.
   *
   * @return List of customers
   */
  public List<Customer> getAllCustomers() {
    return CustomerDAO.getInstance().getAll();
  }
}
