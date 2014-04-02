package com.excilys.computerdatabase.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.computerdatabase.service.ComputerService;

public class DeleteComputerServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(DeleteComputerServlet.class);

	private static final long serialVersionUID = 1643759483804088905L;
	
	@Autowired
	private ComputerService computerService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		logger.debug("DeleteComputerServlet.init()");
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
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
				logger.error("EditComputerServlet invalid computer id failed with: " + e.getMessage());
				resp.sendRedirect("computers");
			}
		}
		
		if( computerService != null && computerId != 0) {
			computerService.deleteComputer(computerId);
		}
		
		req.setAttribute("computerDeleted", true);
		getServletContext().getRequestDispatcher("/computers").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("DeleteComputerServlet.doPost()");
		getServletContext().getRequestDispatcher("/computers").forward(req, resp);
	}
}
