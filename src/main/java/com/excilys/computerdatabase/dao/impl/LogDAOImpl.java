package com.excilys.computerdatabase.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.dao.ConnectionFactory;
import com.excilys.computerdatabase.dao.LogDAO;
import com.excilys.computerdatabase.exception.SQLQueryFailException;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class LogDAOImpl implements LogDAO
{
	final Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
	
	@Autowired
	private ConnectionFactory connectionFactory;
	
	@Autowired
	private BoneCPDataSource boneCP;
	
	public boolean addLog(String log) throws SQLQueryFailException
	{
		logger.debug("addLog(" + log + ")");
		
		PreparedStatement insertLog = null;
		boolean results = false;

		Connection connection = DataSourceUtils.getConnection(boneCP);
		
		try
		{
			insertLog = connection.prepareStatement("INSERT INTO log(query) values(?)");
			insertLog.setString(1, log);
			
			results = insertLog.execute();
		}
		catch (SQLException e)
		{
			throw new SQLQueryFailException(e);
		}

		logger.debug("addLog(" + log + ") successful");
		return results;
	}
}
