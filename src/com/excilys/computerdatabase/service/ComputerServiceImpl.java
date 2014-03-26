package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.dao.QueryBuilder;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.ComputerWrapper;

public class ComputerServiceImpl implements ComputerService{

	private ComputerDAO computerDAO;
	
	public ComputerServiceImpl() {
		computerDAO = ComputerDAO.getInstance();
	}
	
	@Override
	public Computer getComputer(int id) {
		return computerDAO.getComputer(id);
	}

	@Override
	public boolean addComputer(Computer p) {
		return computerDAO.addComputer(p);
	}

	@Override
	public int updateComputer(Computer c) {
		return computerDAO.updateComputer(c);
	}

	@Override
	public boolean deleteComputer(int id) {
		return computerDAO.deleteComputer(id);
	}
	
	@Override
	public ComputerWrapper getComputers(QueryBuilder qb) {
		return new ComputerWrapper(computerDAO.getComputers(qb));
	}

	@Override
	public int getTotalComputers(QueryBuilder qb) {
		return computerDAO.getTotalComputers(qb);
	}
}
