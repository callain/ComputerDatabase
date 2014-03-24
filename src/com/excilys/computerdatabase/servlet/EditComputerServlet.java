package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.CompanyWrapper;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.ServiceFactory;

public class EditComputerServlet extends HttpServlet {
	final Logger logger = LoggerFactory.getLogger(EditComputerServlet.class);
	
	private static final long serialVersionUID = 6136920948547853091L;
	private static ComputerService computerService;
	private static CompanyService companyService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		logger.debug("EditComputerServlet.init()");
		
		computerService = ServiceFactory.getComputerService();
		companyService = ServiceFactory.getCompanyService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("EditComputerServlet.doGet()");
		
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
			Computer computer = computerService.getComputer(computerId);
			req.setAttribute("computer", computer);
		}
		
		if (companyService != null) {
			CompanyWrapper companyWrapper = companyService.getCompanies();
			List<Company> companyList = companyWrapper.getCompanies();
			Collections.sort(companyList);
			req.setAttribute("companies", companyList);
		}

		getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("EditComputerServlet.doPost()");
		
		String pComputerId = req.getParameter("computerId");
		String pName = req.getParameter("name");
		String pIntroduced = req.getParameter("introduced");
		String pDiscontinued = req.getParameter("discontinued");
		String pCompanyId = req.getParameter("company");

		System.out.println(pIntroduced + " 00:00:00");
		System.out.println(pDiscontinued + " 00:00:00");
		
		Timestamp introduced = (pIntroduced != null)? Timestamp.valueOf(pIntroduced + " 00:00:00") : null;
		Timestamp discontinued = (pDiscontinued != null)? Timestamp.valueOf(pDiscontinued + " 00:00:00") : null;

		Computer c = new Computer();
		c.setId(Integer.parseInt(pComputerId));
		c.setName(pName);
		c.setIntroduced(introduced);
		c.setDiscontinued(discontinued);
		c.setCompany(companyService.getCompany(Integer.parseInt(pCompanyId)));

		computerService.updateComputer(c);
		resp.sendRedirect("computers");
	}
}
