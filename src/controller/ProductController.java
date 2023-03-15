package controller;

import java.sql.SQLException;
import java.util.List;

import database.ProductDAO;
import exceptions.EntityNotFoundException;
import model.Product;

public class ProductController {
  /**
   * Get product by barcode
   * 
   * @param barcode
   * @return product
   * @throws EntityNotFoundException
   */
  public Product getProduct(String barcode) throws EntityNotFoundException {
    Product product = ProductDAO.getInstance().get(barcode);
    return product;
  }

  /**
   * Get all products
   * 
   * @return list of products
   * @throws SQLException
   */
  public List<Product> getAllProducts() throws SQLException {
    return ProductDAO.getInstance().getAll();
  }
}
