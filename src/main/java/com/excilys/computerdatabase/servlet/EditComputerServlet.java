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

public class EditComputerServlet extends HttpServlet {
	final Logger logger = LoggerFactory.getLogger(EditComputerServlet.class);
	
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
		logger.debug("EditComputerServlet.init()");
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
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

		if( computerId > 0) {
			Computer computer = computerService.getComputer(computerId);
			ComputerDto cDto = computerMapper.toDto(computer);
			req.setAttribute("computer", cDto);
			CompanyWrapper companyWrapper = companyService.getCompanies();
			List<Company> companyList = companyWrapper.getCompanies();
			req.setAttribute("validation", "0000");
			req.setAttribute("companies", companyList);
			
			getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(req, resp);
		}
		else {
			resp.sendRedirect("computers");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("EditComputerServlet.doPost()");
		
		ComputerDto cDto = ComputerDto.builder()
				.id(req.getParameter("id"))
				.name(req.getParameter("name"))
				.introduced(req.getParameter("introduced"))
				.discontinued(req.getParameter("discontinued"))
				.companyId(req.getParameter("company"))
				.build();
		
		
		// VALIDATION BACK
		String validation = new ComputerValidator().validate(cDto);
		System.out.println(validation);
		if( !validation.equals("0000") ) {
			CompanyWrapper companyWrapper = companyService.getCompanies();
			List<Company> companyList = companyWrapper.getCompanies();
			
			req.setAttribute("validation", validation);
			req.setAttribute("computer", cDto);
			req.setAttribute("companies", companyList);
			getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(req, resp);
		}
		else {
			Computer c = computerMapper.fromDto(cDto);
					
			computerService.updateComputer(c);
			req.setAttribute("computerEdited", true);
			getServletContext().getRequestDispatcher("/computers").forward(req, resp);
		}
	}
}
