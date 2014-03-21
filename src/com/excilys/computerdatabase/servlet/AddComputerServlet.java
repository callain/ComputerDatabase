package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.CompanyWrapper;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.service.ServiceFactory;

public class AddComputerServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6136920948547853091L;
	private static ComputerService computerService;
	private static CompanyService companyService;
	
	@Override
	public void init() throws ServletException {
		super.init();

		computerService = ServiceFactory.getComputerService();
		companyService = ServiceFactory.getCompanyService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (companyService != null) {
			CompanyWrapper companyWrapper = companyService.getCompanies();
			List<Company> companyList = companyWrapper.getCompanies();
			Collections.sort(companyList);
			req.setAttribute("companies", companyList);
		}

		getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String pName = req.getParameter("name");
		String pIntroduced = req.getParameter("introduced");
		String pDiscontinued = req.getParameter("discontinued");
		String pCompanyId = req.getParameter("company");

		Timestamp introduced = (pIntroduced != null)? Timestamp.valueOf(pIntroduced) : null;
		Timestamp discontinued = (pDiscontinued != null)? Timestamp.valueOf(pDiscontinued) : null;

		Computer c = new Computer();
		c.setName(pName);
		c.setIntroduced(introduced);
		c.setDiscontinued(discontinued);
		c.setCompany(companyService.getCompany(Integer.parseInt(pCompanyId)));

		computerService.addComputer(c);
		resp.sendRedirect("computers");
	}
}
