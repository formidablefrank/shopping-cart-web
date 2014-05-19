package com.example.service;

import java.sql.SQLException;

import com.example.dao.DaoException;
import com.example.model.Cart;
import com.example.model.Inventory;

public interface CustomerService {
	Inventory viewProducts() throws DaoException, SQLException;
	void addToCart(String username, String productName, int quantity) throws SQLException, DaoException;
	void removeFromCart(String username, String productName, int quantity) throws SQLException, DaoException;
	void clearCart(String username) throws SQLException, DaoException;
	Cart viewCart(String username) throws SQLException, DaoException;
	void checkOutCart(String username) throws SQLException, DaoException;
}
