package com.example.service;

import java.sql.SQLException;

import com.example.dao.DaoException;
import com.example.dao.category.CategoryDao;
import com.example.dao.category.CategoryDaoImpl;
import com.example.dao.inventory.InventoryDao;
import com.example.dao.inventory.InventoryDaoImpl;
import com.example.dao.product.ProductDao;
import com.example.dao.product.ProductDaoImpl;
import com.example.model.Inventory;
import com.example.model.Product;

public class AdminServiceImpl implements AdminService {
	private ProductDao productDao;
	private InventoryDao inventoryDao;
	private CategoryDao categoryDao;

	public AdminServiceImpl() {
		productDao = new ProductDaoImpl();
		inventoryDao = new InventoryDaoImpl();
		categoryDao = new CategoryDaoImpl();
	}

	@Override
	public void addCategory(String name) throws DaoException, SQLException {
		categoryDao.addCategory(name);
	}

	@Override
	public void addProduct(Product pro, int quantity) throws DaoException, SQLException {
		productDao.addProduct(pro, quantity);
	}

	@Override
	public Inventory viewProducts() throws DaoException, SQLException {
		return inventoryDao.getInventory();
	}

}
