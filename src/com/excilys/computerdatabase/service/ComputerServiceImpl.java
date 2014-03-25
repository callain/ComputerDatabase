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
	public ComputerWrapper getComputers(int offset, int nbRows) {
		return new ComputerWrapper(computerDAO.getComputers(offset, nbRows));
	}

	@Override
	public ComputerWrapper search(String name, int offset, int nbRows) {
		return new ComputerWrapper(computerDAO.search(name, offset, nbRows));
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
	public int getTotalComputers() {
		return computerDAO.getTotalComputers();
	}

	@Override
	public int getTotalComputersForSearch(String name) {
		return computerDAO.getTotalComputersForSearch(name);
	}
}
