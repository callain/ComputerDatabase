package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.ComputerWrapper;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.ServiceFactory;

public class DashboardServlet extends HttpServlet {
	final Logger logger = LoggerFactory.getLogger(DashboardServlet.class);

	private static final long serialVersionUID = 1643759483804088905L;
	private static ComputerService computerService;
	private static final int RESULS_PER_PAGE = 20;
	private int currentPage = 1;
	
	@Override
	public void init() throws ServletException {
		super.init();
		logger.debug("DashboardServlet.init()");
		computerService = ServiceFactory.getComputerService();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("DashboardServlet.doGet()");
		
		// PAGINATION
		String page = req.getParameter("page");
		try {
			currentPage = Integer.parseInt(page);
		}
		catch(NumberFormatException e) {
			logger.warn("DashboardServlet.doGet() Invalid page number failed with : " + e.getMessage() );
			currentPage = 1;
		}
		
		String search = req.getParameter("search");
		if (computerService != null) {
			
			ComputerWrapper computerWrapper;
			List<Computer> computerList;
			int nbComputers;
			if( search == null ) {
				computerWrapper = computerService.getComputers((currentPage - 1) * RESULS_PER_PAGE, RESULS_PER_PAGE);
				nbComputers = computerService.getTotalComputers();
			}
			else {
				computerWrapper = computerService.search(search, (currentPage - 1) * RESULS_PER_PAGE, RESULS_PER_PAGE);
				nbComputers = computerService.getTotalComputersForSearch(search);
				req.setAttribute("search", search);
			}
			
			computerList = computerWrapper.getComputers();
			Collections.sort(computerList);

			req.setAttribute("currentPage", currentPage);
			req.setAttribute("nbPages", (int) Math.ceil((double) (nbComputers / RESULS_PER_PAGE)));
			req.setAttribute("nbComputers", nbComputers );
			req.setAttribute("computers", computerList);
		}

		getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
		logger.debug("DashboardServlet.doPost()");
	}
}