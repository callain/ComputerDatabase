package com.excilys.computerdatabase.domain;

import java.util.ArrayList;
import java.util.List;

public class ComputerWrapper {
	private ArrayList<Computer> Computers;

	public ComputerWrapper() {

	}

	public ComputerWrapper(List<Computer> Computers) {
		this.setComputers((ArrayList<Computer>) Computers);
	}

	public ArrayList<Computer> getComputers() {
		return Computers;
	}

	public void setComputers(ArrayList<Computer> Computers) {
		this.Computers = Computers;
	}
}