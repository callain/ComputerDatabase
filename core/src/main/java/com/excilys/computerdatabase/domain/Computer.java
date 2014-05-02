package com.excilys.computerdatabase.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "computer")
@XmlRootElement
public class Computer implements Comparable<Computer>
{	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "introduced")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime introduced;
	
	@Column(name = "discontinued")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime discontinued;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company_id", referencedColumnName = "id")
	private Company company;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public DateTime getIntroduced() {
		return introduced;
	}

	public DateTime getDiscontinued() {
		return discontinued;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(DateTime introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(DateTime discontinued) {
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
				
		return true;
	}
	
	public String toString() {
		return "Computer => id: " + id + " name: " + name + " introduced: " + introduced + " discontinued : " + discontinued;
	}
}