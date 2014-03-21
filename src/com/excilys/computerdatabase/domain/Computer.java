package com.excilys.computerdatabase.domain;

import java.sql.Timestamp;

public class Computer implements Comparable<Computer>{
	private int id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private Company company;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Timestamp getIntroduced() {
		return introduced;
	}

	public Timestamp getDiscontinued() {
		return discontinued;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Override
	public int compareTo(Computer obj) {
		if (obj == null) {
			return -1;
		}
		
		return this.name.compareTo(obj.name);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if( !(obj instanceof Computer) ) {
			return false;
		}
		
		Computer tmp = (Computer) obj;
		
		if( !tmp.name.equals(this.name) ) {
			return false;
		}
		
		if( !tmp.introduced.equals(this.introduced) ) {
			return false;
		}
		
		if( !tmp.discontinued.equals(this.discontinued) ) {
			return false;
		}
		
//		if( tmp.companyId != this.companyId ) {
//			return false;
//		}
		
		return true;
	}
	
	public String toString() {
		return "Computer => id: " + id + " name: " + name + " introduced: " + introduced + " discontinued : " + discontinued;
	}
}
