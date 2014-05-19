package com.example.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dao.DaoException;
import com.example.model.Category;
import com.example.service.CustomerService;
import com.example.service.CustomerServiceImpl;

public class Cust extends HttpServlet {
	private CustomerService cs;
	
	private static final long serialVersionUID = 1L;
       
    public Cust() {
        super();
        cs = new CustomerServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		com.example.model.Inventory inv = null;
		try {
			inv = cs.viewProducts();
		} catch (DaoException e) {
			request.setAttribute("errorMsg", "Error in database connection. Try again later.");
		} catch (SQLException e) {
			request.setAttribute("errorMsg", "Error in database query. Try again later.");
		}
		if(inv != null){
			request.setAttribute("inventory", inv);
			for(Category cat: inv.getCategories()){
				if(cat.getName().equals(request.getParameter("category")))
					request.setAttribute("category", cat);
			}
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/cust.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
