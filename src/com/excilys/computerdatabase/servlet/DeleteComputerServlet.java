package com.excilys.computerdatabase.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.ServiceFactory;

public class DeleteComputerServlet extends HttpServlet {
	final Logger logger = LoggerFactory.getLogger(DeleteComputerServlet.class);

	private static final long serialVersionUID = 1643759483804088905L;
	private static ComputerService computerService;
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		logger.debug("DeleteComputerServlet.init()");
		
		computerService = ServiceFactory.getComputerService();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("DeleteComputerServlet.doGet()");
		
		String id = req.getParameter("id");
		int computerId = 0;
		if( id != null ) {
			try {
				computerId = Integer.parseInt(id);
			}
			catch(NumberFormatException e) {
				logger.warn("EditComputerServlet invalid computer id failed with: " + e.getMessage());
				resp.sendRedirect("computers");
			}
		}
		
		if( computerService != null && computerId != 0) {
			computerService.deleteComputer(computerId);
		}
		
		resp.sendRedirect("computers");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("DeleteComputerServlet.doPost()");
		resp.sendRedirect("computers");
	}
}
