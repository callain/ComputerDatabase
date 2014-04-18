package com.excilys.computerdatabase.exception;

import java.sql.SQLException;

public class SQLQueryFailException extends RuntimeException {

	private String message;
	public SQLQueryFailException(SQLException e) {
		this.message = e.getMessage();
	}
	
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6764219871413189363L;

}
