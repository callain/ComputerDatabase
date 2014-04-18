package com.excilys.computerdatabase.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computerdatabase.persistence.QueryBuilder;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.wrapper.ComputerWrapper;

@Controller
@RequestMapping("/computers")
public class DashboardController
{
	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
	
	@Autowired
	private ComputerService computerService;
	
	// PAGE
	private static final int RESULTS_PER_PAGE = 12;
	private int currentPage = 1;
	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet (	@RequestParam(value = "page", required = false) String page, @RequestParam(value = "search", required = false) String search,
							@RequestParam(value = "field", required = false) String field, @RequestParam(value = "isDesc", required = false) String isDesc,
							HttpServletRequest req, HttpServletResponse resp )
	{
		logger.debug("DashboardServlet.doGet()");
		
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
		qb.setSearch(search);
		qb.setField(field);
		qb.setOffset((currentPage - 1) * RESULTS_PER_PAGE);
		qb.setNbRows(RESULTS_PER_PAGE);
		qb.setDirection(Boolean.parseBoolean(isDesc));
		qb.setCurrentPage(currentPage);

		ComputerWrapper computerWrapper = computerService.getComputers(qb);
		req.setAttribute("cw", computerWrapper);

		return "dashboard";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String doPost(HttpServletRequest req, HttpServletResponse resp)
	{
		logger.debug("DashboardServlet.doPost()");
		return this.doGet(null, null, null, null, req, resp);
	}
}