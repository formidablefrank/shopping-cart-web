package com.example.dao.cart;

//import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.example.dao.DaoException;

public class CartDaoImplTest {

	@Test
	public void testAddToCart() throws SQLException, DaoException {
		CartDaoImpl cartDaoImpl = new CartDaoImpl();
		cartDaoImpl.addToCart("cus1", "Crop", 3);
	}
	
	@Test
	public void testCheckOutCart() throws SQLException, DaoException {
		CartDaoImpl cartDaoImpl = new CartDaoImpl();
		cartDaoImpl.checkOutCart("cus1");
	}
	
	@Test
	public void testClearCart() throws SQLException, DaoException {
		CartDaoImpl cartDaoImpl = new CartDaoImpl();
		cartDaoImpl.clearCart("cus1");
	}
	
	@Test
	public void testRemoveFromCart() throws SQLException, DaoException {
		CartDaoImpl cartDaoImpl = new CartDaoImpl();
		cartDaoImpl.removeFromCart("cus1", "Crop", 1);
	}
	
	@Test
	public void updateAllCart() throws DaoException, SQLException {
		CartDaoImpl cartDaoImpl = new CartDaoImpl();
		cartDaoImpl.updateAllCart("Crop", 60);;
	}

}