package com.excilys.computerdatabase.servlet;

import java.io.IOException;
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
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.validator.ComputerValidator;
import com.excilys.computerdatabase.wrapper.CompanyWrapper;

public class AddComputerServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);
	private static final long serialVersionUID = 6136920948547853091L;
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerMapper computerMapper;
	
	@Override
	public void init() throws ServletException {
		super.init();
		logger.debug("AddComputerServlet.init()");
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("AddComputerServlet.doGet()");
		
		CompanyWrapper companyWrapper = companyService.getCompanies();
		List<Company> companyList = companyWrapper.getCompanies();
		req.setAttribute("validation", "0000");
		req.setAttribute("companies", companyList);

		getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("AddComputerServlet.doPost()");
		
		ComputerDto cDto = ComputerDto.builder()
		.name(req.getParameter("name"))
		.introduced(req.getParameter("introduced"))
		.discontinued(req.getParameter("discontinued"))
		.companyId(req.getParameter("company"))
		.build();
		
		// VALIDATION BACK
		String validation = new ComputerValidator().validate(cDto);
		
		if( !validation.equals("0000") ) {
			CompanyWrapper companyWrapper = companyService.getCompanies();
			List<Company> companyList = companyWrapper.getCompanies();
			req.setAttribute("companies", companyList);
			req.setAttribute("validation", validation);
			req.setAttribute("computer", cDto);
			getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(req, resp);
		}
		else {
			Computer c = computerMapper.fromDto(cDto);
			
			computerService.addComputer(c);
			req.setAttribute("computerAdded", true);
			getServletContext().getRequestDispatcher("/computers").forward(req, resp);
		}
	}
}
