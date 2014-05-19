package com.example.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dao.DaoException;
import com.example.model.Product;
import com.example.service.AdminService;
import com.example.service.AdminServiceImpl;

public class ProcessAddProduct extends HttpServlet {
	private AdminService as;
	
	private static final long serialVersionUID = 1L;
       
    public ProcessAddProduct() {
        super();
        as = new AdminServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tempName = request.getParameter("name");
		String tempPrice = request.getParameter("price");
		String tempCat = request.getParameter("category");
		String tempQty = request.getParameter("qty");
		
		if(tempName == null || tempPrice == null || tempCat == null || tempQty == null ||
				tempName.equals("") || tempPrice.equals("") || tempCat.equals("") || tempQty.equals("")){
			request.setAttribute("errorMsg", "One or more fields are blank!");
		}
		else if(!tempPrice.matches("[0-9]+(.[0-9]{1,2})?")){
			request.setAttribute("errorMsg", "Invalid argument for price! Up to two decimal places only.");
		}
		else if(!tempQty.matches("[0-9]+")){
			request.setAttribute("errorMsg", "Invalid argument for quantity!");
		}
		else{
			Product pro = new Product(tempName, tempCat, new BigDecimal(tempPrice), "img/product.jpg");
			try {
				as.addProduct(pro, Integer.parseInt(tempQty));
				request.setAttribute("successMsg", "Success!");
			} catch (NumberFormatException e) {
				request.setAttribute("errorMsg", "Invalid argument for price/quantity!");
			} catch (DaoException e) {
				request.setAttribute("errorMsg", "Error in database connection. Try again later.");
			} catch (SQLException e) {
				request.setAttribute("errorMsg", "Duplicate product name! Try again.");
			}
		}
		request.getServletContext().getRequestDispatcher("/addProduct").forward(request, response);
	}

}
