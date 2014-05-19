package com.example.service;

import java.sql.SQLException;

import com.example.model.User;
import com.example.dao.DaoException;
import com.example.dao.user.UserDao;
import com.example.dao.user.UserDaoImpl;

public class LoginServiceImpl implements LoginService {
	private UserDao userDao;
	
	public LoginServiceImpl() {
		userDao = new UserDaoImpl();
	}

	@Override
	public User getUser(String username) throws DaoException, SQLException {
		return userDao.getUser(username);
	}

	@Override
	public int getKeyUser(String username) throws DaoException, SQLException {
		return userDao.getKeyUser(username);
	}

}
