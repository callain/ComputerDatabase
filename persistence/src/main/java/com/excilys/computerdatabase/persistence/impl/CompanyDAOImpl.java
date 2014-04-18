package com.excilys.computerdatabase.persistence.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.exception.SQLQueryFailException;
import com.excilys.computerdatabase.persistence.CompanyDAO;
import com.excilys.computerdatabase.persistence.ConnectionFactory;
import com.excilys.computerdatabase.rowmapper.CompanyRowMapper;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class CompanyDAOImpl implements CompanyDAO
{
	private final static Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);
	
	@Autowired
	private ConnectionFactory connectionFactory;
	
	@Autowired
	private BoneCPDataSource boneCP;
	
	@Override
	public Company getCompany(int id) throws SQLQueryFailException
	{
		logger.debug("getCompany(" + id + ")");

		JdbcTemplate jdbc = new JdbcTemplate(boneCP);
		
		List<Company> companyList;
		try
		{
			companyList = jdbc.query("SELECT * from company WHERE id = ?", new Object[] { id }, new CompanyRowMapper() );
			logger.debug("getCompany(" + id + ") successful");
		}
		catch (DataAccessException e)
		{
			throw new SQLQueryFailException(e);
		}
		if( !companyList.isEmpty() ) return companyList.get(0);
		else return null;
	}

	@Override
	public List<Company> getCompanies()  throws SQLQueryFailException
	{
		logger.debug("getCompanies()");

		JdbcTemplate jdbc = new JdbcTemplate(boneCP);

		List<Company> companies;
		try
		{
			companies = jdbc.query("SELECT id, name FROM company ORDER BY company.name", new CompanyRowMapper());
			logger.debug("getCompanies() successful");
		}
		catch (DataAccessException e)
		{
			throw new SQLQueryFailException(e);
		}
		
		return companies;
	}
}