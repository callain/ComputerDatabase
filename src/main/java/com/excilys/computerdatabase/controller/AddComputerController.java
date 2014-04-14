package com.excilys.computerdatabase.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.wrapper.CompanyWrapper;

@Controller
@RequestMapping("/addComputer")
public class AddComputerController
{
	private static final Logger logger = LoggerFactory.getLogger(AddComputerController.class);
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerMapper computerMapper;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(Model model) 
	{
		logger.debug("AddComputerServlet.doGet()");
		
		CompanyWrapper companyWrapper = companyService.getCompanies();
		List<Company> companyList = companyWrapper.getCompanies();

		model.addAttribute("validation", "0000");
		model.addAttribute("companies", companyList);
		model.addAttribute("computer", new ComputerDto());
		
		return "addComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(@Valid @ModelAttribute("computer") ComputerDto cDto, BindingResult result, HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException, ServletException
	{
		logger.debug("AddComputerServlet.doPost()");
		
		String validation = "";
		
		if( result.hasErrors() )
		{
			CompanyWrapper companyWrapper = companyService.getCompanies();
			List<Company> companyList = companyWrapper.getCompanies();
			
			if( result.hasFieldErrors("name") ) validation += "1";
			else validation += "0";
			
			if( result.hasFieldErrors("introduced") ) validation += "1";
			else validation += "0";
			
			if( result.hasFieldErrors("discontinued") ) validation += "1";
			else validation += "0";
			
			if( result.hasFieldErrors("companyId") ) validation += "1";
			else validation += "0";
			
			req.setAttribute("validation", validation);
			req.setAttribute("companies", companyList);
			req.setAttribute("computer", cDto);
			return "addComputer";
		}
		else
		{
			Computer c = computerMapper.fromDto(cDto);
			computerService.addComputer(c);
			req.setAttribute("computerAdded", true);
			return "forward:computers";
		}
	}
}
