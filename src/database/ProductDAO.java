package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import exceptions.EntityNotFoundException;
import model.Product;

public class ProductDAO implements DAOInterface<Product, String> {
	private static ProductDAO instance;

	private final String FIND_PRODUCT_BY_BARCODE = "SELECT * FROM Product JOIN Store_Product ON barcode = productBarcode WHERE barcode = ?";
	private final String SELECT_ALL_PRODUCTS = "SELECT * FROM Product JOIN Store_Product ON barcode = productBarcode";

	/**
	 * Constructor for ProductDAO.
	 */
	private ProductDAO() {
	}

	/**
	 * Returns the singleton instance of ProductDAO.
	 *
	 * @return the singleton instance of ProductDAO
	 */
	public static ProductDAO getInstance() {
		if (instance == null) {
			instance = new ProductDAO();
		}
		return instance;
	}

	/**
	 * Returns a Product object with the given barcode.
	 *
	 * @param barcode the barcode of the Product to be returned
	 * @return the Product with the given barcode
	 * @throws EntityNotFoundException
	 */
	public Product get(String barcode) throws EntityNotFoundException {
		Connection connection = DBConnection.getInstance().getDBconnection();
		Product product = null;
		try {
			PreparedStatement ps = connection.prepareStatement(FIND_PRODUCT_BY_BARCODE,
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, barcode);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				product = buildObject(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (product == null) {
			throw new EntityNotFoundException("Registration with barcode " + barcode + " not found");
		}

		return product;
	}

	/**
	 * Inserts a Product into the database.
	 *
	 * @param product the Product to be inserted
	 * @throws SQLException
	 */
	@Override
	public void insert(Product t) throws SQLException {
		throw new UnsupportedOperationException("Method not implemented");
	}

	/**
	 * Deletes a Product from the database.
	 *
	 * @param id the id of the Product to be deleted
	 * @throws EntityNotFoundException, SQLException
	 */
	@Override
	public void delete(String id) throws EntityNotFoundException, SQLException {
		throw new UnsupportedOperationException("Method not implemented");
	}

	/**
	 * Updates a Product in the database.
	 *
	 * @param product the Product to be updated
	 * @throws EntityNotFoundException, SQLException
	 */
	@Override
	public void update(Product product) throws EntityNotFoundException, SQLException {
		throw new UnsupportedOperationException("Method not implemented");
	}

	/**
	 * Returns a list of all Products in the database.
	 *
	 * @return a list of all Products in the database
	 * @throws SQLException
	 */
	@Override
	public List<Product> getAll() throws SQLException {
		Connection connection = DBConnection.getInstance().getDBconnection();
		List<Product> products = new ArrayList<>();

		try {
			PreparedStatement ps = connection.prepareStatement(SELECT_ALL_PRODUCTS, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product product = buildObject(rs);
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}

	/**
	 * Builds a Product object from a ResultSet.
	 *
	 * @param rs the ResultSet from which the Product is to be built
	 * @return the Product object
	 * @throws SQLException
	 */
	private Product buildObject(ResultSet rs) throws SQLException {
		double salesPrice = rs.getFloat("salesPrice");
		Product product = new Product(rs.getString("barcode"), UUID.fromString(rs.getString("storeId")),
				rs.getString("name"), rs.getInt("stock"), salesPrice);
		return product;
	}
}
