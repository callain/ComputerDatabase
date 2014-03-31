package com.excilys.computerdatabase.exception;

import java.sql.SQLException;

public class SQLQueryFailException extends RuntimeException {

	public SQLQueryFailException(SQLException e) {
		this.setStackTrace(e.getStackTrace());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6764219871413189363L;

}
