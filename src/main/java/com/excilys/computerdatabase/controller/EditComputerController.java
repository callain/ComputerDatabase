package com.excilys.computerdatabase.controller;

import java.io.IOException;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.wrapper.CompanyWrapper;

@Controller
@RequestMapping("/editComputer")
public class EditComputerController
{
	final Logger logger = LoggerFactory.getLogger(EditComputerController.class);
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ComputerMapper computerMapper;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(@RequestParam("id") String id, HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException
	{
		logger.debug("EditComputerServlet.doGet()");
		
		int computerId = 0;
		
		if( id != null )
		{
			try
			{
				computerId = Integer.parseInt(id);
			}
			catch(NumberFormatException e)
			{
				logger.warn("EditComputerServlet invalid computer id failed with: " + e.getMessage());
				return "forward:computers";
			}
		}

		if( computerId > 0)
		{
			Computer computer = computerService.getComputer(computerId);
			ComputerDto cDto = computerMapper.toDto(computer);
			CompanyWrapper companyWrapper = companyService.getCompanies();
			List<Company> companyList = companyWrapper.getCompanies();
			req.setAttribute("computer", cDto);
			req.setAttribute("validation", "0000");
			req.setAttribute("companies", companyList);

			return "editComputer";
		}
		else
		{
			return "forward:computers";
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(@Valid @ModelAttribute("computer") ComputerDto cDto, BindingResult result, HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException
	{
		logger.debug("EditComputerServlet.doPost()");
		
		String validation = "";
		
		if( result.hasErrors() ) {
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
			req.setAttribute("computer", cDto);
			req.setAttribute("companies", companyList);
			return "editComputer";
		}
		else {
			Computer c = computerMapper.fromDto(cDto);
					
			computerService.updateComputer(c);
			req.setAttribute("computerEdited", true);
			return "forward:computers";
		}
	}
}