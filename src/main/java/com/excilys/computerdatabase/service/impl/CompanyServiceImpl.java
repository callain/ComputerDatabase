package com.excilys.computerdatabase.service.impl;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.dao.ConnectionFactory;
import com.excilys.computerdatabase.dao.impl.CompanyDAOImpl;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.CompanyWrapper;
import com.excilys.computerdatabase.service.CompanyService;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

	private final static Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);

	@Autowired
	private ConnectionFactory connectionFactory;
	
	@Autowired
	private CompanyDAOImpl companyDAO;
	
	@Override
	public Company getCompany(int id)
	{
		logger.debug("getCompany(" + id + ")");
		Company c = null;
		try
		{
			c = companyDAO.getCompany(id);
			logger.debug("getCompany(" + id + ") successful");
		}
		catch (SQLException e)
		{
			logger.error("getCompany(" + id + ") failed with: " + e.getMessage());
		}
		finally
		{
			connectionFactory.closeConnection();
		}
		
		return c;
	}
	
	@Override
	public CompanyWrapper getCompanies()
	{
		logger.debug("getCompanies()");
		CompanyWrapper computerWrapper = null;
		try
		{
			computerWrapper = new CompanyWrapper(companyDAO.getCompanies());
			logger.debug("getCompanies() sucessful");
		}
		catch(SQLException e)
		{
			logger.error("getCompanies() failed with: " + e.getMessage() );
		}
		finally
		{
			connectionFactory.closeConnection();
		}
		
		return computerWrapper;
	}
}
