package com.example.dao.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.dao.ConnectionManager;
import com.example.dao.DaoException;
import com.example.dao.category.CategoryDao;
import com.example.dao.category.CategoryDaoImpl;
import com.example.model.Category;
import com.example.model.Inventory;

public class InventoryDaoImpl implements InventoryDao{

	public InventoryDaoImpl() {
		
	}

	@Override
	public Inventory getInventory() throws DaoException, SQLException {
		CategoryDao categoryDao = new CategoryDaoImpl();
		Inventory inventory = null;
		List<Category> list = new ArrayList<>();
		Connection con = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT fld_category_name FROM tbl_category");
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			Category category = categoryDao.getCategory(rs.getString("fld_category_name"));
			list.add(category);
		}
		inventory = new Inventory(list);
		rs.close();
		stmt.close();
		con.close();
		return inventory;
	}

	@Override
	public void decreaseSupply(String productname, int quantity)
			throws DaoException, SQLException {
		Connection con = ConnectionManager.getInstance().getConnection();
		PreparedStatement stm = con.prepareStatement("UPDATE tbl_product SET fld_inventory_qty = fld_inventory_qty - ? WHERE fld_product_name = ?;");
		stm.setInt(1, quantity);
		stm.setString(2, productname);
		int rset = stm.executeUpdate();
		stm.close();
		con.close();
	}

}
