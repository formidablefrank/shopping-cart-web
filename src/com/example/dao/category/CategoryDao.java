package com.example.dao.category;

import java.sql.SQLException;

import com.example.dao.DaoException;
import com.example.model.Category;

public interface CategoryDao {
	Category getCategory(String name) throws SQLException, DaoException;
	void addCategory(String name) throws DaoException, SQLException;
}
