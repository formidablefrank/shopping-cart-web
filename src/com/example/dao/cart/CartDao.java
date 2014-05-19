package com.example.dao.cart;

import java.sql.SQLException;

import com.example.dao.DaoException;
import com.example.model.Cart;

public interface CartDao {
	Cart getCart(String username) throws SQLException, DaoException;
	int getKeyOrder(String username) throws SQLException, DaoException;
	void addToCart(String username, String productname, int quantity) throws SQLException, DaoException;
	void checkOutCart(String username) throws SQLException, DaoException;
	void removeFromCart(String username, String productname, int quantity) throws SQLException, DaoException;
	void clearCart(String username) throws SQLException, DaoException;
}
