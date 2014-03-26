package com.excilys.computerdatabase.dao;

public class QueryBuilder {
	
	// LIMIT 
	private int offset;
	private int nbRows;
	
	// SEARCH
	private String search;
	
	// SORTED
	private String field;
	
	private boolean direction;
	
	public QueryBuilder() {
		offset = 0;
		nbRows = 0;
		search = "";
		field = "";
		direction = false;
	}

	public int getOffset() {
		return offset;
	}

	public int getNbRows() {
		return nbRows;
	}

	public String getSearch() {
		return search;
	}

	public String getField() {
		return field;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void setNbRows(int nbRows) {
		this.nbRows = nbRows;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public void setField(String field) {
		this.field = field;
	}

	public boolean getDirection() {
		return direction;
	}

	public void setDirection(boolean direction) {
		this.direction = direction;
	}
}
