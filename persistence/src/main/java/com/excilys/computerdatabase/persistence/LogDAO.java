package com.excilys.computerdatabase.persistence;

import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.exception.SQLQueryFailException;

public interface LogDAO {
	public void addLog(Log log) throws SQLQueryFailException;
}
