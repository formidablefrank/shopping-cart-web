package com.example.dao.product;

import java.sql.SQLException;

import com.example.dao.DaoException;
import com.example.model.Product;

public interface ProductDao {
	Product getProduct(String name) throws DaoException, SQLException;
	int getInventoryQty(String name) throws DaoException, SQLException;
	int getKeyProduct(String name) throws DaoException, SQLException;
	void addProduct(Product pro, int quantity) throws DaoException, SQLException;
}
