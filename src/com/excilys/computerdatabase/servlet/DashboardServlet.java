package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dao.ComputerField;
import com.excilys.computerdatabase.dao.QueryBuilder;
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
		
		// PARAMETERS
		String page = req.getParameter("page");
		String fieldSort = req.getParameter("field");
		String orderBy = req.getParameter("orderBy");
		String search = req.getParameter("search");

		// PAGINATION
		try {
			currentPage = Integer.parseInt(page);
		}
		catch(NumberFormatException e) {
			logger.warn("DashboardServlet.doGet() Invalid page number failed with : " + e.getMessage() );
			currentPage = 1;
		}

		// QUERY TO SEND
		QueryBuilder qb = new QueryBuilder();
		qb.setSearch(search);
		qb.setField((fieldSort != null)? ComputerField.valueOf(fieldSort).getName() : ComputerField.NAME.getName() );
		qb.setOffset((currentPage - 1) * RESULS_PER_PAGE);
		qb.setNbRows(RESULS_PER_PAGE);
		
		if( orderBy != null && !orderBy.isEmpty() && orderBy.equals("ASC") ) {
			qb.setDirection(true);
		}

		// CALL computerService if available
		if (computerService != null) {
 			ComputerWrapper computerWrapper;
			List<Computer> computerList;
			int nbComputers;
			
			computerWrapper = computerService.getComputers(qb);
			nbComputers = computerService.getTotalComputers(qb);
			computerList = computerWrapper.getComputers();

			req.setAttribute("search", search);
			
			Map<ComputerField, String> computerFieldSort = new HashMap<>();
			ComputerField[] computerField = ComputerField.values();
			for(int i = 0 ; i < computerField.length ; i++ ) {
				if( qb.getField().equals(computerField[i].getName()) ) {
					if( qb.getDirection() ) computerFieldSort.put(computerField[i], "DESC");
				}
				else computerFieldSort.put(computerField[i], "ASC");
			}
			
			req.setAttribute("computerField", computerField);
			req.setAttribute("computerFieldSort", computerFieldSort);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("nbPages", (int) Math.ceil((double) nbComputers / (double) RESULS_PER_PAGE));
			req.setAttribute("nbComputers", nbComputers);
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
