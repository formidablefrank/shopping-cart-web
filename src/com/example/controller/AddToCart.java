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

public class AddToCart extends HttpServlet {
	private CustomerService cs;
	
	private static final long serialVersionUID = 1L;
       
    public AddToCart() {
        super();
        cs = new CustomerServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			cs.addToCart((String) request.getSession().getAttribute("username"), request.getParameter("productName"), Integer.parseInt(request.getParameter("quantity")));
			request.setAttribute("successMsg", "Success!");
		} catch (NumberFormatException e) {
			request.setAttribute("errorMsg", "Invalid argument for quantity!");
		} catch (SQLException e) {
			request.setAttribute("errorMsg", "Error in database connection. Try again later.");
		} catch (DaoException e) {
			request.setAttribute("errorMsg", "Error in database query. Try again later.");
		}
		request.getServletContext().getRequestDispatcher("/cust?category=" + request.getParameter("category")).forward(request, response);
	}

}
