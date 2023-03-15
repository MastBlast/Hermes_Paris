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
import model.Store;

public class StoreDAO implements DAOInterface<Store, UUID> {
  private static StoreDAO instance;

  private final String SELECT_ALL_STORES = "SELECT * FROM Store";
  private final String FIND_STORE_BY_ID = """
      SELECT *
      FROM Store
      WHERE [id] = ? """;

  /**
   * Constructor for StoreDAO.
   */
  private StoreDAO() {
  }

  /**
   * Returns the singleton instance of StoreDAO.
   *
   * @return the singleton instance of StoreDAO
   */
  public static StoreDAO getInstance() {
    if (instance == null) {
      instance = new StoreDAO();
    }
    return instance;
  }

  /**
   * Get store by id.
   *
   * @param id the id
   * @return the store
   * @throws EntityNotFoundException
   */
  @Override
  public Store get(UUID identifier) throws EntityNotFoundException {
    Connection connection = DBConnection.getInstance().getDBconnection();
    Store store = null;
    try {
      PreparedStatement ps = connection.prepareStatement(FIND_STORE_BY_ID, Statement.RETURN_GENERATED_KEYS);

      ps.setString(1, identifier.toString());

      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        store = buildObject(rs);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    if (store == null) {
      throw new EntityNotFoundException("Store with id " + identifier + " not found");
    }

    return store;
  }

  /**
   * Get all stores.
   *
   * @return the list
   */
  @Override
  public List<Store> getAll() {
    List<Store> stores = new ArrayList<>();
    Connection connection = DBConnection.getInstance().getDBconnection();
    try {
      PreparedStatement ps = connection.prepareStatement(SELECT_ALL_STORES, Statement.RETURN_GENERATED_KEYS);

      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        stores.add(buildObject(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return stores;
  }

  /**
   * Insert store.
   *
   * @param store the store to insert
   */
  @Override
  public void insert(Store t) {
    throw new UnsupportedOperationException("Method not implemented");
  }

  /**
   * Delete store.
   *
   * @param id the id of the store to delete
   */
  @Override
  public void delete(UUID id) throws EntityNotFoundException {
    throw new UnsupportedOperationException("Method not implemented");
  }

  /**
   * Update store.
   *
   * @param store the store to update
   */
  @Override
  public void update(Store T) throws EntityNotFoundException, SQLException {
    throw new UnsupportedOperationException("Method not implemented");
  }

  /**
   * Builds the object.
   *
   * @param rs the result set
   * @return the store
   * @throws SQLException the SQL exception
   */
  private Store buildObject(ResultSet rs) throws SQLException {
    Store store = new Store(UUID.fromString(rs.getString("id")), rs.getString("zipCode"), rs.getString("address"),
        (rs.getInt("customerCapacity")));
    return store;
  }

}
