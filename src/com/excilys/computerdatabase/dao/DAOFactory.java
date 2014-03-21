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

public class DAOFactory {

	public static Connection getConnection() {
		Logger logger = LoggerFactory.getLogger(DAOFactory.class);
		logger.debug("Connection creation...");
		
		Context ctx;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/computer");
			logger.debug("Connection successful...");
			
			return ds.getConnection();
		} catch (NamingException e) {
			logger.debug("Connection failed with: " + e.getMessage());
		} catch (SQLException e) {
			logger.debug("Connection failed with: " + e.getMessage());
		}
		
		return null;
	}

	public static void closeObject(Connection co, ResultSet rs, PreparedStatement st) {
		Logger logger = LoggerFactory.getLogger(DAOFactory.class);
		logger.debug("Closing connection, resultset, preparedStatement...");
		try {
			if (co != null) {
				co.close();
			}

			if (rs != null) {
				rs.close();
			}

			if (st != null) {
				st.close();
			}
			logger.debug("Closing successful...");
		} catch (SQLException e) {
			logger.warn("Failed closing connection, resultset, preparedStatement with:" + e.getMessage());
		}
	}
}