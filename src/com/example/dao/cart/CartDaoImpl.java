package com.example.dao.cart;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.example.dao.ConnectionManager;
import com.example.dao.DaoException;
import com.example.dao.inventory.InventoryDao;
import com.example.dao.inventory.InventoryDaoImpl;
import com.example.dao.product.ProductDao;
import com.example.dao.product.ProductDaoImpl;
import com.example.dao.user.UserDao;
import com.example.dao.user.UserDaoImpl;
import com.example.model.Cart;
import com.example.model.Product;

public class CartDaoImpl implements CartDao {
	private InventoryDao inventoryDao;
	private UserDao userDao;
	private ProductDao productDao;

	public CartDaoImpl() {
		inventoryDao = new InventoryDaoImpl();
		userDao = new UserDaoImpl();
		productDao = new ProductDaoImpl();
	}

	@Override
	public Cart getCart(String username) throws SQLException, DaoException {
		Cart cart = null;
		Map<Product, Integer> list = new LinkedHashMap<>();
		
		Connection con = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM tbl_order_detail NATURAL JOIN tbl_order NATURAL JOIN tbl_product NATURAL JOIN tbl_category NATURAL JOIN tbl_user WHERE fld_username = ?;");
		stmt.setString(1, username);
		ResultSet rs = stmt.executeQuery();
		BigDecimal amount = null;
		
		while(rs.next()){
			Product pro = new Product(rs.getString("fld_product_name"), rs.getString("fld_category_name"), new BigDecimal(rs.getString("fld_unit_price")), rs.getString("fld_product_image"));
			list.put(pro, rs.getInt("fld_quantity"));
			amount = rs.getBigDecimal("fld_amount");
		}
		cart = new Cart(list, username, amount);
		rs.close();
		stmt.close();
		con.close();
		return cart;
	}
	
	private ResultSet getOrderedProductDetail(String username, String productname) throws DaoException, SQLException{
		Connection con = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM tbl_order NATURAL JOIN tbl_order_detail NATURAL JOIN tbl_user NATURAL JOIN tbl_product WHERE fld_product_name = ? AND fld_username = ?;");
		stmt.setString(1, productname);
		stmt.setString(2, username);
		return stmt.executeQuery();
	}

	@Override
	public void addToCart(String username, String productname, int quantity)
			throws SQLException, DaoException {
		Connection con = ConnectionManager.getInstance().getConnection();
		ResultSet rs = getOrderedProductDetail(username, productname);
		
		int keyUser = userDao.getKeyUser(username);
		
		if(rs.next()){
			int keyOrderDetail = rs.getInt("key_order_detail");
			PreparedStatement stmt2 = con.prepareStatement("UPDATE tbl_order_detail SET fld_quantity = fld_quantity + ? WHERE key_order_detail = ?;");
			stmt2.setInt(1, quantity);
			stmt2.setInt(2, keyOrderDetail);
			int rs2 = stmt2.executeUpdate();
			stmt2.close();
			rs.close();
		}
		else{
			//get keyOrder from that user
			int keyOrder = getKeyOrder(username);
			if(keyOrder == 0){
				PreparedStatement stmt6 = con.prepareStatement("INSERT INTO tbl_order (key_user, fld_amount) VALUES (?, 0);");
				stmt6.setInt(1, keyUser);
				int rs6 = stmt6.executeUpdate();
				stmt6.close();
			}
			keyOrder = getKeyOrder(username);
			
			//get keyProduct from that product
			int keyProduct = productDao.getKeyProduct(productname);
			
			//insert to orderDetail
			PreparedStatement stmt3 = con.prepareStatement("INSERT INTO tbl_order_detail (key_order, key_product, fld_quantity) VALUES (?, ?, ?);");
			stmt3.setInt(1, keyOrder);
			stmt3.setInt(2, keyProduct);
			stmt3.setInt(3, quantity);
			int rs3 = stmt3.executeUpdate();
			stmt3.close();
		}
		con.close();
		updateOrder(username);
		updateAllCart(productname, quantity);
	}
	
	public void updateOrder(String username) throws SQLException, DaoException {
		Connection con = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt8 = con.prepareStatement("SELECT key_order, key_user, SUM(fld_quantity * fld_unit_price) AS total FROM tbl_order NATURAL JOIN tbl_order_detail NATURAL JOIN tbl_product NATURAL JOIN tbl_user WHERE fld_username = ?");
		stmt8.setString(1, username);
		ResultSet rs8 = stmt8.executeQuery();
		BigDecimal amount = new BigDecimal("0.00");
		if(rs8.next()){
			amount = rs8.getBigDecimal("total");
		}
		rs8.close();
		stmt8.close();
		
		if(amount == null) amount = new BigDecimal("0.00");
		
		PreparedStatement stmt9 = con.prepareStatement("UPDATE tbl_order SET fld_amount = ? WHERE key_user = ?;");
		stmt9.setBigDecimal(1, amount);
		stmt9.setInt(2, userDao.getKeyUser(username));
		int rs9 = stmt9.executeUpdate();
		stmt9.close();
		con.close();
	}
	
	private void removeZeroQuantityFromCart(int keyProduct) throws DaoException, SQLException{
		Connection con = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("DELETE FROM tbl_order_detail WHERE fld_quantity = 0;");
		int rs = stmt.executeUpdate();
		stmt.close();
		con.close();
	}
	
	public void updateAllCart(String productName, int quantity) throws DaoException, SQLException{
		int keyProduct = productDao.getKeyProduct(productName);
		int invQty = productDao.getInventoryQty(productName);
		Connection con = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM tbl_order_detail WHERE key_product = ?");
		stmt.setInt(1, keyProduct);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			int tempQty = rs.getInt("fld_quantity");
			if(tempQty >= invQty){
				PreparedStatement stmt2 = con.prepareStatement("UPDATE tbl_order_detail SET fld_quantity = ? WHERE key_product = ? AND key_order_detail = ?;");
				stmt2.setInt(1, invQty);
				stmt2.setInt(2, keyProduct);
				stmt2.setInt(3, rs.getInt("key_order_detail"));
				int rs2 = stmt2.executeUpdate();
				stmt2.close();
			}
		}
		stmt.close();
		rs.close();
		con.close();
		
		updateAllAmount();
		removeZeroQuantityFromCart(keyProduct);
	}

	private void updateAllAmount() throws DaoException, SQLException {
		Connection con = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM tbl_order NATURAL JOIN tbl_user");
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			updateOrder(rs.getString("fld_username"));
		}
		rs.close();
		stmt.close();
		con.close();
	}

	@Override
	public void checkOutCart(String username) throws SQLException, DaoException {
		Connection con = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT key_order, fld_product_name, fld_quantity FROM tbl_order NATURAL JOIN tbl_order_detail NATURAL JOIN tbl_product NATURAL JOIN tbl_user WHERE fld_username = ?;");
		stmt.setString(1, username);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			String proName = rs.getString("fld_product_name");
			int quantity = rs.getInt("fld_quantity");
			inventoryDao.decreaseSupply(proName, quantity);
			updateAllCart(proName, quantity);
		}
		rs.close();
		stmt.close();
		
		PreparedStatement stmt2 = con.prepareStatement("DELETE FROM tbl_order WHERE key_order = ?");
		stmt2.setInt(1, getKeyOrder(username));
		int rs2 = stmt2.executeUpdate();
		stmt2.close();
		con.close();
	}

	@Override
	public int getKeyOrder(String username) throws SQLException, DaoException {
		Connection con = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt4 = con.prepareStatement("SELECT key_order FROM tbl_order NATURAL JOIN tbl_user WHERE fld_username = ?;");
		stmt4.setString(1, username);
		ResultSet rs4 = stmt4.executeQuery();
		int keyOrder = 0;
		if(rs4.next())
			keyOrder = rs4.getInt("key_order");
		stmt4.close();
		con.close();
		return keyOrder;
	}

	@Override
	public void clearCart(String username) throws SQLException, DaoException {
		Connection con = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt2 = con.prepareStatement("DELETE FROM tbl_order_detail WHERE key_order = ?");
		stmt2.setInt(1, getKeyOrder(username));
		int rs2 = stmt2.executeUpdate();
		stmt2.close();
		con.close();
		updateOrder(username);
	}

	@Override
	public void removeFromCart(String username, String productname, int quantity)
			throws SQLException, DaoException {
		ResultSet rs = getOrderedProductDetail(username, productname);
		Connection con = null;
		while(rs.next()){
			int keyOrderDetail = rs.getInt("key_order_detail");
			con = ConnectionManager.getInstance().getConnection();
			PreparedStatement stmt2 = con.prepareStatement("UPDATE tbl_order_detail SET fld_quantity = fld_quantity - ? WHERE key_order_detail = ?;");
			stmt2.setInt(1, quantity);
			stmt2.setInt(2, keyOrderDetail);
			int rs2 = stmt2.executeUpdate();
			stmt2.close();
		}
		con.close();
		rs.close();
		removeZeroQuantityFromCart(productDao.getKeyProduct(productname));
		updateOrder(username);
	}

}
