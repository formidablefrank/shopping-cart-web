package com.example.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dao.DaoException;
import com.example.service.AdminService;
import com.example.service.AdminServiceImpl;

public class Inventory extends HttpServlet {
	private AdminService as;
	
	private static final long serialVersionUID = 1L;
       
    public Inventory() {
        super();
        as = new AdminServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		com.example.model.Inventory inv = null;
		try {
			inv = as.viewProducts();
		} catch (DaoException e) {
			request.setAttribute("errorMsg", "Error in database connection. Try again later.");
		} catch (SQLException e) {
			request.setAttribute("errorMsg", "Error in database query. Try again later.");
		}
		if(inv != null){
			request.setAttribute("inventory", inv.getCategories());
		}
		request.getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
