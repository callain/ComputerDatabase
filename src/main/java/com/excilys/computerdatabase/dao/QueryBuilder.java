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
	
	private int currentPage;
	
	public QueryBuilder() {
		offset = 0;
		nbRows = 0;
		search = "";
		field = "";
		direction = false;
		currentPage = 1;
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
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Search: " + search);
		sb.append(", Field: " + field);
		sb.append(", Offset: " + offset);
		sb.append(", NbRows: " + nbRows);
		sb.append(", Direction: " + direction);
		
		return sb.toString();
	}
}