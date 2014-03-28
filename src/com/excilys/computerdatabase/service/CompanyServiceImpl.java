package com.excilys.computerdatabase.service;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dao.CompanyDAOImpl;
import com.excilys.computerdatabase.dao.ConnectionFactory;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.CompanyWrapper;

public enum CompanyServiceImpl implements CompanyService {

	INSTANCE;
	
	private final static Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);
	
	private CompanyDAOImpl companyDAO;
	{
		companyDAO = CompanyDAOImpl.INSTANCE;
	}
	
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
			ConnectionFactory.INSTANCE.closeConnection();
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
			ConnectionFactory.INSTANCE.closeConnection();
		}
		
		return computerWrapper;
	}
}
