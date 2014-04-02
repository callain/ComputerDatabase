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
import org.springframework.stereotype.Component;

import com.jolbox.bonecp.BoneCPDataSource;

@Component
public class ConnectionFactory {

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
	
	private final ThreadLocal<Connection> connection = new ThreadLocal<Connection>()
	{
		@Override  
	 	protected Connection initialValue()
		{  
			try
			{
				return boneCP.getConnection();
			}
			catch (SQLException e)
			{
				logger.error("Can't get a new connection: " + e.getMessage());
			}
			return null;
		}
	};
	
	public Connection getConnection() {
		logger.debug("getConnection()");
		return connection.get();
	}
	
	public void startTransaction() {
		try {
			getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
	
	public void commitTransaction() {
		try {
			getConnection().commit();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
	
	public void rollback() {
		logger.error("Connection is being rollback()");
		try {
			getConnection().rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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