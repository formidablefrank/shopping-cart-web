package com.example.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dao.DaoException;
import com.example.service.CustomerService;
import com.example.service.CustomerServiceImpl;

public class CheckOut extends HttpServlet {
	private CustomerService cs;
	
	private static final long serialVersionUID = 1L;
       
    public CheckOut() {
        super();
        cs = new CustomerServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			cs.checkOutCart((String)request.getSession().getAttribute("username"));
		} catch (SQLException e) {
			request.setAttribute("errorMsg", "Error in database connection. Try again later.");
		} catch (DaoException e) {
			request.setAttribute("errorMsg", "Error in database query. Try again later.");
		}
		response.sendRedirect("cust");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
