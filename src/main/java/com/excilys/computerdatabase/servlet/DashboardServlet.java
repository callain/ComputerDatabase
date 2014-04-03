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

import com.excilys.computerdatabase.dao.QueryBuilder;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.wrapper.ComputerWrapper;

public class DashboardServlet extends HttpServlet {
	
	private static final Logger logger = LoggerFactory.getLogger(DashboardServlet.class);
	private static final long serialVersionUID = 1643759483804088905L;
	
	@Autowired
	private ComputerService computerService;
	
	// PAGE
	private static final int RESULTS_PER_PAGE = 12;
	private int currentPage = 1;
	
	@Override
	public void init() throws ServletException {
		super.init();
		logger.debug("DashboardServlet.init()");
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("DashboardServlet.doGet()");
		
		// PARAMETERS
		String page = req.getParameter("page");

		// PAGINATION
		if( page == null || page.isEmpty() ) {
			currentPage = 1;
		}
		else {
			try {
				currentPage = Integer.parseInt(page);
			}
			catch(NumberFormatException e) {
				logger.warn("DashboardServlet.doGet() Invalid page number failed with : " + e.getMessage() );
				currentPage = 1;
			}
		}

		QueryBuilder qb = new QueryBuilder();
		qb.setSearch(req.getParameter("search"));
		qb.setField(req.getParameter("field"));
		qb.setOffset((currentPage - 1) * RESULTS_PER_PAGE);
		qb.setNbRows(RESULTS_PER_PAGE);
		qb.setDirection(Boolean.parseBoolean(req.getParameter("isDesc")));
		qb.setCurrentPage(currentPage);

		ComputerWrapper computerWrapper = computerService.getComputers(qb);

		req.setAttribute("cw", computerWrapper);

		getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
		logger.debug("DashboardServlet.doPost()");
	}
}