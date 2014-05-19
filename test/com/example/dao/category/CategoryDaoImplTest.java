package com.example.dao.category;

//import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.example.dao.DaoException;

public class CategoryDaoImplTest {

	@Test
	public void testAddCategory() throws DaoException, SQLException {
		new CategoryDaoImpl().addCategory("CategoryE");
	}

}
