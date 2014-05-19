package com.example.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.dao.DaoException;
import com.example.model.User;
import com.example.service.LoginService;
import com.example.service.LoginServiceImpl;

public class ProcessLogin extends HttpServlet {
	private LoginService loginService;
	
	private static final long serialVersionUID = 1L;
       
    public ProcessLogin() {
        super();
        loginService = new LoginServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = null;
		try {
			user = loginService.getUser(request.getParameter("username"));
		} catch (DaoException e) {
			request.setAttribute("errorMsg", "Error in database connection. Try again later.");
		} catch (SQLException e) {
			request.setAttribute("errorMsg", "Error in database query. Try again later.");
		}
		if(user != null && user.getPassword().equals(request.getParameter("password"))){
			request.getSession(true);
			HttpSession hs = request.getSession();
			hs.setAttribute("username", user.getUsername());
			response.sendRedirect(user.getRole());
		}
		else{
			request.setAttribute("loginMsg", "Invalid credentials. Try again.");
			RequestDispatcher rd = request.getRequestDispatcher("/home");
			rd.forward(request, response);
		}
	}

}
