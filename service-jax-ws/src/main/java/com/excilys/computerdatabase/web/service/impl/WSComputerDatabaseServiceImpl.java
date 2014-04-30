package com.excilys.computerdatabase.web.service.impl;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.persistence.QueryBuilder;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.web.service.WSComputerDatabaseService;
import com.excilys.computerdatabase.wrapper.CompanyWrapper;
import com.excilys.computerdatabase.wrapper.ComputerWrapper;

@WebService(endpointInterface="com.excilys.computerdatabase.web.service.WSComputerDatabaseService")
public class WSComputerDatabaseServiceImpl implements WSComputerDatabaseService
{
	private static final Logger logger = LoggerFactory.getLogger(WSComputerDatabaseServiceImpl.class);
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;
	
	@Override
	public Computer getComputer(int id)
	{
		logger.debug("WSComputerServiceImpl.getComputer(" + id + ")");
		
		Computer c = computerService.getComputer(id);
		
		return c;
	}

	@Override
	public int addComputer(Computer c)
	{
		logger.debug("WSComputerServiceImpl.addComputer(" + c + ")");
		int computerId = 0;
		
		computerId = computerService.addComputer(c);

		return computerId;
	}

	@Override
	public void updateComputer(Computer c)
	{
		logger.debug("WSComputerServiceImpl.updateComputer(" + c + ")");
		
		computerService.updateComputer(c);
	}

	@Override
	public void deleteComputer(int id)
	{
		logger.debug("WSComputerServiceImpl.deleteComputer(" + id + ")");
		
		computerService.deleteComputer(id);
	}
	
	@Override
	public ComputerWrapper getComputers(QueryBuilder qb)
	{
		logger.debug("WSComputerServiceImpl.getComputers(" + qb + ")");

		ComputerWrapper computerWrapper = computerService.getComputers(qb);
		
		return computerWrapper;
	}
	
	@Override
	public Company getCompany(int id)
	{
		logger.debug("getCompany(" + id + ")");
		
		Company c = null;
		c = companyService.getCompany(id);
		
		return c;
	}
	
	@Override
	public CompanyWrapper getCompanies()
	{
		logger.debug("getCompanies()");
		
		CompanyWrapper companyWrapper = null;
		companyWrapper = companyService.getCompanies();
		
		return companyWrapper;
	}
}