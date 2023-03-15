package tests.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import controller.StoreController;
import exceptions.EntityNotFoundException;
import model.Store;

class TestStoreController {
	StoreController storeController = new StoreController();

	@Test
	void testGetStore() throws EntityNotFoundException {
		Store store = null;
		store = storeController.getStore(UUID.fromString("3AAAAAAA-BBBB-CCCC-DDDD-2EEEEEEEEEEE"));
		assertEquals(store.getId(), UUID.fromString("3AAAAAAA-BBBB-CCCC-DDDD-2EEEEEEEEEEE"));
	}

	@Test
	void testGetAllStores() throws SQLException {
		List<Store> stores = null;
		stores = storeController.getAllStores();
		assertNotNull(stores);
	}

}
