package com.example.service;

import java.sql.SQLException;

import com.example.dao.DaoException;
import com.example.model.Inventory;
import com.example.model.Product;

public interface AdminService {
	void addCategory(String name) throws DaoException, SQLException;
	void addProduct(Product pro, int quantity) throws DaoException, SQLException;
	Inventory viewProducts() throws DaoException, SQLException;
}
