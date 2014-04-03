package com.excilys.computerdatabase.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.dao.ComputerField;
import com.excilys.computerdatabase.domain.Computer;

public class ComputerWrapper {
	private ArrayList<Computer> Computers;
	private int pages;
	private int results;
	private int currentPage;
	private boolean isDesc;
	private ComputerField[] computerFields;
	private String search;
	private ComputerField field;
	
	public ComputerWrapper() {
		setComputerFields(ComputerField.values());
	}

	public ComputerWrapper(List<Computer> Computers) {
		setComputerFields(ComputerField.values());
		this.setComputers((ArrayList<Computer>) Computers);
	}

	public ArrayList<Computer> getComputers() {
		return Computers;
	}

	public void setComputers(ArrayList<Computer> Computers) {
		this.Computers = Computers;
	}

	public int getPages() {
		return pages;
	}

	public int getResults() {
		return results;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public void setResults(int results) {
		this.results = results;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public boolean getIsDesc() {
		return isDesc;
	}

	public void setDesc(boolean isDesc) {
		this.isDesc = isDesc;
	}

	public ComputerField[] getComputerFields() {
		return computerFields;
	}

	public void setComputerFields(ComputerField[] computerFields) {
		this.computerFields = computerFields;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public ComputerField getField() {
		return field;
	}

	public void setField(ComputerField field) {
		this.field = field;
	}
}