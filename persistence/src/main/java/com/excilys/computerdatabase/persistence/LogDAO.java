package com.excilys.computerdatabase.persistence;

import com.excilys.computerdatabase.exception.SQLQueryFailException;

public interface LogDAO {
	public boolean addLog(String log) throws SQLQueryFailException;
}
