package com.example.dao.product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.dao.ConnectionManager;
import com.example.dao.DaoException;
import com.example.model.Product;

public class ProductDaoImpl implements ProductDao {

	public ProductDaoImpl() {
		
	}

	@Override
	public Product getProduct(String name) throws DaoException, SQLException {
		Product pro = null;
		
		Connection con = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM tbl_product NATURAL JOIN tbl_category_name WHERE fld_product_name = ? ;");
		stmt.setString(1, name);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()){
			pro = new Product(rs.getString("fld_product_name"), rs.getString("fld_category"), new BigDecimal(rs.getString("fld_unit_price")), rs.getString("fld_product_image"));
		}
		rs.close();
		stmt.close();
		con.close();
		
		return pro;
	}

	@Override
	public void addProduct(Product pro, int invQuantity) throws DaoException, SQLException{
		int category = 0;
		Connection con = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT key_category FROM tbl_category WHERE fld_category_name = ? ;");
		stmt.setString(1, pro.getCategory());
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			category = rs.getInt("key_category");
		}
		rs.close();
		stmt.close();
		
		PreparedStatement stmt2 = con.prepareStatement("INSERT INTO tbl_product (`key_category`, `fld_product_name`, `fld_inventory_qty`, `fld_unit_price`, `fld_product_image`) VALUES (?, ?, ?, ?, ?);");
		stmt2.setInt(1, category);
		stmt2.setString(2, pro.getName());
		stmt2.setInt(3, invQuantity);
		stmt2.setBigDecimal(4, pro.getPrice());
		stmt2.setString(5, pro.getImage());
		int rs2 = stmt2.executeUpdate();
		stmt2.close();
		con.close();
	}

	@Override
	public int getKeyProduct(String name) throws DaoException, SQLException {
		Connection con = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt4 = con.prepareStatement("SELECT key_product FROM tbl_product WHERE fld_product_name = ?;");
		stmt4.setString(1, name);
		ResultSet rs4 = stmt4.executeQuery();
		int keyProduct = 0;
		if(rs4.next())
			keyProduct = rs4.getInt("key_product");
		rs4.close();
		stmt4.close();
		con.close();
		return keyProduct;
	}

	@Override
	public int getInventoryQty(String name) throws DaoException, SQLException {
		Connection con = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt4 = con.prepareStatement("SELECT fld_inventory_qty FROM tbl_product WHERE fld_product_name = ?;");
		stmt4.setString(1, name);
		ResultSet rs4 = stmt4.executeQuery();
		int invQty = 0;
		if(rs4.next())
			invQty = rs4.getInt("fld_inventory_qty");
		rs4.close();
		stmt4.close();
		con.close();
		return invQty;
	}

}
