package com.excilys.computerdatabase.domain;

public class Company implements Comparable<Company> {
	private int id;
	private String name;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Company o) {
		return this.name.compareTo(o.name);
	}
}
