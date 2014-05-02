package com.excilys.computerdatabase.wrapper;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.persistence.ComputerField;

@XmlRootElement
public class ComputerWrapper {
	
	private List<Computer> computers;
	
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

	public ComputerWrapper(List<Computer> computers) {
		setComputerFields(ComputerField.values());
		this.setComputers(computers);
	}

	@XmlElement(name = "computer")
	public List<Computer> getComputers() {
		return computers;
	}

	public void setComputers(List<Computer> computers) {
		this.computers = computers;
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