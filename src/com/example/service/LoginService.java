package com.example.service;

import java.sql.SQLException;

import com.example.dao.DaoException;
import com.example.model.User;

public interface LoginService {
	User getUser(String username) throws DaoException, SQLException;
	int getKeyUser(String username) throws DaoException, SQLException;
}
