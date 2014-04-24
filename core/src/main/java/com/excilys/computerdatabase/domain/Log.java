package com.excilys.computerdatabase.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "log")
public class Log
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "query")
	private String query;
	
	@Column(name = "date")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime date;
	
	public Log()
	{
		
	}
	
	public Log(String query)
	{
		this.query = query;
	}

	public int getId() {
		return id;
	}

	public String getQuery() {
		return query;
	}

	public DateTime getDate() {
		return date;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}
}
