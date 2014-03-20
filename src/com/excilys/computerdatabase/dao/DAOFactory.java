package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DAOFactory {
//	private static DAOFactory factory;
//
//	private DAOFactory() {
//	}
//
//	public static DAOFactory getInstance() {
//		if (factory != null)
//			return factory;
//
//		factory = new DAOFactory();
//		return factory;
//	}

	public static Connection getConnection() {
		Context ctx;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/computer");
			return ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}