package com.example.filter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.dao.DaoException;
import com.example.service.LoginService;
import com.example.service.LoginServiceImpl;

public class Cust implements Filter {
	private LoginService loginService;
	
    public Cust() {
        loginService = new LoginServiceImpl();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hssr = (HttpServletRequest) request;
		HttpSession hs = hssr.getSession();
		String username = (String) hs.getAttribute("username");
		String userRole = null;
		if(username == null){
			HttpServletResponse hsr = (HttpServletResponse) response;
			hsr.sendRedirect("logout");
			return;
		}
		try {
			userRole = loginService.getUser(username).getRole();
		} catch (DaoException e) {
			request.setAttribute("errorMsg", "Error in database connection. Try again later.");
		} catch (SQLException e) {
			request.setAttribute("errorMsg", "Error in database query. Try again later.");
		}
		if(userRole != null && !userRole.equals("cust")){
			HttpServletResponse hsr = (HttpServletResponse) response;
			hsr.sendRedirect("logout");
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
