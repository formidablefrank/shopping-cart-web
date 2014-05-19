package com.example.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.dao.ConnectionManager;
import com.example.dao.DaoException;
import com.example.model.User;

public class UserDaoImpl implements UserDao {

	public UserDaoImpl() {
		
	}

	@Override
	public User getUser(String name) throws DaoException, SQLException {
		User user = null;
		
		Connection con = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT fld_username, fld_password, fld_roletype FROM tbl_user NATURAL JOIN tbl_role WHERE fld_username = ? ;");
		stmt.setString(1, name);
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()){
			user = new User(rs.getString("fld_username"), rs.getString("fld_password"), rs.getString("fld_roletype"));
		}
		rs.close();
		stmt.close();
		con.close();
		
		return user;
	}

	@Override
	public int getKeyUser(String name) throws DaoException, SQLException {
		Connection con = ConnectionManager.getInstance().getConnection();
		PreparedStatement stmt7 = con.prepareStatement("SELECT key_user FROM tbl_user WHERE fld_username = ?;");
		stmt7.setString(1, name);
		ResultSet rs7 = stmt7.executeQuery();
		int keyUser = 0;
		if(rs7.next())
			keyUser = rs7.getInt("key_user");
		rs7.close();
		stmt7.close();
		con.close();
		return keyUser;
	}

}
