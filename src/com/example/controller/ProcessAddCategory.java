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

public class ProcessAddCategory extends HttpServlet {
	private AdminService as;
	
	private static final long serialVersionUID = 1L;
       
    public ProcessAddCategory() {
        super();
        as = new AdminServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tempCategory = request.getParameter("category");
		if(tempCategory == null || tempCategory.equals("")){
			request.setAttribute("errorMsg", "Field is blank!");
		}
		else{
			try {
				as.addCategory(tempCategory);
				request.setAttribute("successMsg", "Success!");
			} catch (DaoException e) {
				request.setAttribute("errorMsg", "Error in database connection. Try again later.");
			} catch (SQLException e) {
				request.setAttribute("errorMsg", "Duplicate category name! Please try another.");
			}
		}
		request.getServletContext().getRequestDispatcher("/addCategory").forward(request, response);
	}

}
