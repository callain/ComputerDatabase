package com.excilys.computerdatabase.persistence.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.exception.SQLQueryFailException;
import com.excilys.computerdatabase.persistence.LogDAO;

@Repository
public class LogDAOImpl implements LogDAO
{
	final Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
	
	@Autowired
	private JdbcTemplate jdbc;
	
	public boolean addLog(String log) throws SQLQueryFailException
	{
		logger.debug("addLog(" + log + ")");
		
		int results = 0;

		try
		{
			results = jdbc.update("INSERT INTO log(query) values(?)", new Object[] { log });
		}
		catch (DataAccessException e)
		{
			throw new SQLQueryFailException(e);
		}

		logger.debug("addLog(" + log + ") successful");
		return results >= 1;
	}
}