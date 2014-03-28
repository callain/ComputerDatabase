package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum LogDAOImpl implements LogDAO{
	
	INSTANCE;
	
	final Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
	
	public boolean addLog(String log) throws SQLException {
		logger.debug("addLog(" + log + ")");
		
		PreparedStatement insertLog = null;
		boolean results = false;

		Connection connection = ConnectionFactory.INSTANCE.getConnection();
		
		insertLog = connection.prepareStatement("INSERT INTO log(query) values(?)");
		insertLog.setString(1, log);
		
		results = insertLog.execute();

		logger.debug("addLog(" + log + ") successful");
		return results;
	}
}
