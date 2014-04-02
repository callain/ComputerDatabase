package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.CompanyWrapper;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

public class AddComputerServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);
	private static final long serialVersionUID = 6136920948547853091L;
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		logger.debug("AddComputerServlet.init()");
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("AddComputerServlet.doGet()");
		
		if (companyService != null) {
			CompanyWrapper companyWrapper = companyService.getCompanies();
			List<Company> companyList = companyWrapper.getCompanies();
			req.setAttribute("companies", companyList);
		}

		getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("AddComputerServlet.doPost()");
		
		String pName = req.getParameter("name");
		String pIntroduced = req.getParameter("introduced");
		String pDiscontinued = req.getParameter("discontinued");
		String pCompanyId = req.getParameter("company");
		
		// VALIDATION BACK
		boolean validation = true;
		if( pName == null || pName.length() < 2 ) {
			req.setAttribute("name", true);
			validation = false;
		}

		int companyId = 0;
		try {
			companyId = Integer.parseInt(pCompanyId);
		}
		catch(NumberFormatException e) {
			req.setAttribute("companyId", true);
			validation = false;
		}
		
		Timestamp introduced = (pIntroduced != null && !pIntroduced.equals(""))? Timestamp.valueOf(pIntroduced + " 00:00:00") : null;
		Timestamp discontinued = (pDiscontinued != null && !pDiscontinued.equals(""))? Timestamp.valueOf(pDiscontinued + " 00:00:00") : null;

		if( validation ) {
			Computer c = new Computer();
			c.setName(pName);
			c.setIntroduced(introduced);
			c.setDiscontinued(discontinued);
			c.setCompany(companyService.getCompany(companyId));
			
			computerService.addComputer(c);
			req.setAttribute("computerAdded", true);
			getServletContext().getRequestDispatcher("/computers").forward(req, resp);
		}
		else {
			getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(req, resp);
		}
	}
}
