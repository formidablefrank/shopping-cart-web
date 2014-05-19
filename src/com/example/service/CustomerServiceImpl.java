package com.example.service;

import java.sql.SQLException;

import com.example.dao.DaoException;
import com.example.dao.cart.CartDao;
import com.example.dao.cart.CartDaoImpl;
import com.example.dao.inventory.InventoryDao;
import com.example.dao.inventory.InventoryDaoImpl;
import com.example.model.Cart;
import com.example.model.Inventory;

public class CustomerServiceImpl implements CustomerService {

	private InventoryDao inventoryDao;
	private CartDao cartDao;

	public CustomerServiceImpl() {
		inventoryDao = new InventoryDaoImpl();
		cartDao = new CartDaoImpl();
	}

	@Override
	public Inventory viewProducts() throws DaoException, SQLException {
		return inventoryDao.getInventory();
	}

	@Override
	public void addToCart(String username, String productName, int quantity) throws SQLException, DaoException {
		cartDao.addToCart(username, productName, quantity);
	}

	@Override
	public void removeFromCart(String username, String productName, int quantity) throws SQLException, DaoException {
		cartDao.removeFromCart(username, productName, quantity);
	}

	@Override
	public void clearCart(String userName) throws SQLException, DaoException {
		cartDao.clearCart(userName);
	}

	@Override
	public Cart viewCart(String username) throws SQLException, DaoException {
		return cartDao.getCart(username);
	}

	@Override
	public void checkOutCart(String username) throws SQLException, DaoException {
		cartDao.checkOutCart(username);
	}

}
