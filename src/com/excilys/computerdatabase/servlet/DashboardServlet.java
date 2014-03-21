package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.ComputerWrapper;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.ServiceFactory;

public class DashboardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1643759483804088905L;
	private static ComputerService computerService;
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		computerService = ServiceFactory.getComputerService();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (computerService != null) {
			ComputerWrapper computerWrapper = computerService.getComputers();
			List<Computer> computerList = computerWrapper.getComputers();
			Collections.sort(computerList);
			req.setAttribute("computers", computerList);
		}

		getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}
}
