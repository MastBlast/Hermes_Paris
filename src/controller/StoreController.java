package controller;

import java.util.List;
import java.util.UUID;

import database.StoreDAO;
import exceptions.EntityNotFoundException;
import model.Store;

public class StoreController {
  /**
   * Gets a store by id.
   *
   * @param id The id of the store to get.
   * @return The store with the given id.
   * @throws EntityNotFoundException
   */
  public Store getStore(UUID identifier) throws EntityNotFoundException {
    return StoreDAO.getInstance().get(identifier);
  }

  /**
   * Gets all stores.
   *
   * @return A list of all stores.
   */
  public List<Store> getAllStores() {
    return StoreDAO.getInstance().getAll();
  }
}
