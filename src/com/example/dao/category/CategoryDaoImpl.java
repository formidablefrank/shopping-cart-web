package com.example.dao.category;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.example.dao.ConnectionManager;
import com.example.dao.DaoException;
import com.example.model.Category;
import com.example.model.Product;

public class CategoryDaoImpl implements CategoryDao {

	public CategoryDaoImpl() {
		
	}

	@Override
	public Category getCategory(String name) throws SQLException, DaoException {
		Category category = null;
		Map<Product, Integer> list = new LinkedHashMap<>();
		
		Connection con = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM tbl_product NATURAL JOIN tbl_category WHERE fld_category_name = ? ;");
		stmt.setString(1, name);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()){
			Product pro = new Product(rs.getString("fld_product_name"), rs.getString("fld_category_name"), new BigDecimal(rs.getString("fld_unit_price")), rs.getString("fld_product_image"));
			list.put(pro, rs.getInt("fld_inventory_qty"));
		}
		category = new Category(list, name);
		rs.close();
		stmt.close();
		con.close();
		
		return category;
	}
	@Override
	public void addCategory(String name) throws DaoException, SQLException {
		Connection con = ConnectionManager.getInstance().getConnection();
		PreparedStatement stm = con.prepareStatement("INSERT INTO tbl_category (fld_category_name) VALUES (?);");
		stm.setString(1, name);
		int rset = stm.executeUpdate();
		stm.close();
		con.close();
	}
}
