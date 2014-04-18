package com.excilys.computerdatabase.persistence;

import java.util.List;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.exception.SQLQueryFailException;

public interface ComputerDAO {
	public Computer getComputer(int id) throws SQLQueryFailException;
	public List<Computer> getComputers(QueryBuilder qb) throws SQLQueryFailException;
	public int getTotalComputers(QueryBuilder qb) throws SQLQueryFailException;
	public int addComputer(Computer c) throws SQLQueryFailException;
	public int updateComputer(Computer c) throws SQLQueryFailException;
	public boolean deleteComputer(int id) throws SQLQueryFailException;
}
