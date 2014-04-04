package com.excilys.computerdatabase.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.computerdatabase.service.ComputerService;

@Controller
@RequestMapping("/deleteComputer")
public class DeleteComputerController {
	private static final Logger logger = LoggerFactory.getLogger(DeleteComputerController.class);
	
	@Autowired
	private ComputerService computerService;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(HttpServletRequest req, HttpServletResponse resp, Model model)
	{
		logger.debug("DeleteComputerServlet.doGet()");
		
		String id = req.getParameter("id");
		int computerId = 0;
		if( id != null ) {
			try {
				computerId = Integer.parseInt(id);
			}
			catch(NumberFormatException e) {
				logger.error("EditComputerServlet invalid computer id failed with: " + e.getMessage());
			}
		}
		
		if( computerService != null && computerId != 0) {
			computerService.deleteComputer(computerId);
			req.setAttribute("computerDeleted", true);
		}
		
		return "forward:computers";
	}
}