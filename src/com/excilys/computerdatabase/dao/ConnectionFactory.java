package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jolbox.bonecp.BoneCPDataSource;

public enum ConnectionFactory {

	INSTANCE;
	
	private BoneCPDataSource boneCP  = new BoneCPDataSource();
	final Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
	
	{
		Context ctx;
		DataSource ds;
		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/computer");
			boneCP.setDatasourceBean(ds);
			logger.debug("Loading datasource successful");
		} catch (NamingException e) {
			logger.debug("Loading datasource failed with: " + e.getMessage());
		}
	}
	
	private final ThreadLocal<Connection> connection = new ThreadLocal<Connection>(){
		@Override  
	 	protected Connection initialValue() {  
			try {
				return boneCP.getConnection();
			} catch (SQLException e) {
					
			}
			return null;
		} 
	};
	
	public Connection getConnection() {
		Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
		logger.debug("getConnection()");

		try {
			if( connection.get() == null ) {
				connection.set(boneCP.getConnection());
			}
		} catch (SQLException e) {
			logger.debug("getConnection() failed with: " + e.getMessage());
		}
    	
		return connection.get();
	}

	public void closeObject(ResultSet rs, PreparedStatement st) {
		logger.debug("Closing resultset, preparedStatement");
		try {
			if (rs != null) {
				rs.close();
			}
			if (st != null) {
				st.close();
			}
			logger.debug("Closing successful");
		} catch (SQLException e) {
			logger.error("Failed closing resultset, preparedStatement with:" + e.getMessage());
		}
	}

	public void closeConnection() {
		logger.debug("Closing connection");
		try {
			this.connection.get().close();
			logger.debug("Closing successful");
		} catch (SQLException e) {
			logger.error("Failed closing connection with:" + e.getMessage());
		}
		this.connection.remove();
	}
}