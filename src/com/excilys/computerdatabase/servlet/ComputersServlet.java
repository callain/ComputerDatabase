package com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.dao.CompanyDAO;
import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;

public class ComputersServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1643759483804088905L;
	private ComputerDAO computers;
	private CompanyDAO companies;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		computers = ComputerDAO.getInstance();
		companies = CompanyDAO.getInstance();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if( computers != null ) {
			List<Computer> computerList = computers.getComputers();
			// SORT
			Collections.sort(computerList);
			req.setAttribute("computers", computerList);
		}
		
		if( companies != null ) {
			Map<Integer, Company> companyMap = companies.getCompanies();
			req.setAttribute("companies", companyMap);
		}
		
		getServletContext().getRequestDispatcher("/dashboard.jsp").forward(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String introduced = req.getParameter("introduced");
		String discontinued = req.getParameter("discontinued");
		String companyId = req.getParameter("company_id");
		
		Computer c = new Computer();
		c.setName(name);
		c.setIntroduced(new Timestamp(0));
		c.setDiscontinued(new Timestamp(0));
		c.setCompanyId(Integer.parseInt(companyId));
		computers.addComputer(null);
	}
}
