package com.excilys.computerdatabase.persistence;

public enum ComputerField
{
	ID("computer.id"),
	NAME("computer.name"),
	INTRODUCED("computer.introduced"),
	DISCONTINUED("computer.discontinued"),
	COMPANY_NAME("company.name");
	
	private String name;
	
	private ComputerField(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}