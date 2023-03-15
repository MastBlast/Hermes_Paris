package model;

import java.util.UUID;

public class Product {
  private String barcode;
  private UUID storeId;
  private String name;
  private int stock;
  private double salesPrice;

  /**
   * Constructor for Product
   * 
   * @param barcode
   * @param storeId
   * @param name
   * @param stock
   * @param salesPrice
   */
  public Product(String barcode, UUID storeId, String name, int stock, double salesPrice) {
    this.barcode = barcode;
    this.storeId = storeId;
    this.name = name;
    this.stock = stock;
    this.salesPrice = salesPrice;
  }

  /**
   * @return the barcode
   */
  public String getBarcode() {
    return barcode;
  }

  /**
   * @param barcode the barcode to set
   */
  public void setBarcode(String barcode) {
    this.barcode = barcode;
  }

  /**
   * @return the storeId
   */
  public UUID getStoreId() {
    return storeId;
  }

  /**
   * @param storeId the storeId to set
   */
  public void setStoreId(UUID storeId) {
    this.storeId = storeId;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the stock
   */
  public int getStock() {
    return stock;
  }

  /**
   * @param stock the stock to set
   */
  public void setStock(int stock) {
    this.stock = stock;
  }

  /**
   * @return the price
   */
  public double getPrice() {
    return salesPrice;
  }

  /**
   * @param price the price to set
   */
  public void setPrice(double price) {
    this.salesPrice = price;
  }

  @Override
  public String toString() {
    return name;
  }

}
