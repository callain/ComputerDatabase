package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.ComputerWrapper;

public interface ComputerService {
	
	public Computer getComputer(int id);
	public ComputerWrapper getComputers(int offset, int nbRows);
	public boolean addComputer(Computer c);
	public int updateComputer(Computer c);
	public boolean deleteComputer(int id);
	public ComputerWrapper search(String name, int offset, int nbRows);
	public int getTotalComputers();
}
