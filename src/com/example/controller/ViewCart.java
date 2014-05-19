package com.example.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dao.DaoException;
import com.example.model.Cart;
import com.example.service.CustomerService;
import com.example.service.CustomerServiceImpl;

public class ViewCart extends HttpServlet {
	private CustomerService cs;
	
	private static final long serialVersionUID = 1L;
       
    public ViewCart() {
        super();
        cs = new CustomerServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cart cart = null;
		try {
			cart = cs.viewCart((String) request.getSession().getAttribute("username"));
			request.setAttribute("cart", cart);
		} catch (SQLException e) {
			request.setAttribute("errorMsg", "Error in database connection. Try again later.");
		} catch (DaoException e) {
			request.setAttribute("errorMsg", "Error in database query. Try again later.");
		}
		request.getServletContext().getRequestDispatcher("/WEB-INF/viewCart.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
