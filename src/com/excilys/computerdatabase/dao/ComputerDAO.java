package com.excilys.computerdatabase.dao;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.domain.Computer;

public interface ComputerDAO {
	public Computer getComputer(int id) throws SQLException;
	public List<Computer> getComputers(QueryBuilder qb) throws SQLException;
	public int getTotalComputers(QueryBuilder qb) throws SQLException;
	public int addComputer(Computer c) throws SQLException;
	public int updateComputer(Computer c) throws SQLException;
	public boolean deleteComputer(int id) throws SQLException;
}
