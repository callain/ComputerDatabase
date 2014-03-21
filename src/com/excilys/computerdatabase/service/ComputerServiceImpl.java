package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.dao.ComputerDAO;
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
	public ComputerWrapper getComputers() {
		return new ComputerWrapper(computerDAO.getComputers());
	}
}
