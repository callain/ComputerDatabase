package com.excilys.computerdatabase.persistence;

public enum ComputerField
{
	ID("id"),
	NAME("name"),
	INTRODUCED("introduced"),
	DISCONTINUED("discontinued"),
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