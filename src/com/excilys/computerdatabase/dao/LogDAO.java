package com.excilys.computerdatabase.dao;

import java.sql.SQLException;

public interface LogDAO {
	public boolean addLog(String log) throws SQLException;
}
