package com.example.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dao.DaoException;
import com.example.model.Category;
import com.example.model.Inventory;
import com.example.service.AdminService;
import com.example.service.AdminServiceImpl;

public class AddProduct extends HttpServlet {
	private AdminService as;
	
	private static final long serialVersionUID = 1L;
       
    public AddProduct() {
        super();
        as = new AdminServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Inventory inv = null;
		try {
			inv = as.viewProducts();
		} catch (DaoException e) {
			request.setAttribute("errorMsg", "Error in database connection (getting categories). Try again later.");

		} catch (SQLException e) {
			request.setAttribute("errorMsg", "Error in database query (getting categories). Try again later.");

		}
		if(inv != null){
			List<String> categoryList = new ArrayList<String>();
			for(Category cat: inv.getCategories()){
				categoryList.add(cat.getName());
			}
			request.setAttribute("categoryList", categoryList);
		}
		
		request.getServletContext().getRequestDispatcher("/WEB-INF/addProduct.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
