package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.persistence.QueryBuilder;
import com.excilys.computerdatabase.wrapper.ComputerWrapper;

public interface ComputerService {
	
	public Computer getComputer(int id);
	public ComputerWrapper getComputers(QueryBuilder qb);
	public int addComputer(Computer c);
	public int updateComputer(Computer c);
	public boolean deleteComputer(int id);
	public int getTotalComputers(QueryBuilder qb);
}
