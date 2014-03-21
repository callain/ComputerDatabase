package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.ComputerWrapper;

public interface ComputerService {
	
	public Computer getComputer(int id);
	public boolean addComputer(Computer c);
	public ComputerWrapper getComputers();
}
