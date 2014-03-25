package com.excilys.computerdatabase.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class BoostrapPaginatorTag extends TagSupport {

	private static final long serialVersionUID = -6236303539051034386L;

	private String search;
	private int currentPage;
	private int totalPages;
	
	@Override
	public int doStartTag() throws JspException {
		//Get the writer object for output.
        JspWriter out = pageContext.getOut();

        try {
        	// BEGIN LIST
			out.println("<ul class=\"pagination\">");
			
			// FIRST PAGE
			if( currentPage != 1 ) {
				if( search != null && !search.equals("") ) {
					out.println("<li><a href=\"computers?page=" + 1 + "&search=" + search + "\">&laquo;</a></li>");
				}
				else {
					out.println("<li><a href=\"computers?page=" + 1 + "\">&laquo;</a></li>");
				}
			}
			else {
				out.println("<li class=\"disabled\"><a href=\"computers?page=" + 1 + "\">&laquo;</a></li>");
			}
			
			// PREVIOUS PAGE
			if( currentPage == 1 ) {
				out.println("<li class=\"disabled\"><a href=\"#\">&lt;</a></li>");
			} 
			else {
				if( search != null && !search.equals("")) {
					out.println("<li><a href=\"computers?page=" + (currentPage - 1) + "&search=" + search + "\">&lt;</a></li>");
				}
				else {
					out.println("<li><a href=\"computers?page=" + (currentPage - 1) + "\">&lt;</a></li>");
				}
			}
			
			// CURRENT PAGE - 2
			for(int i = currentPage - 2 ; i < currentPage ; i++ ) {
				if( i >= 1) {
					if( search != null && !search.equals("") ) {
						out.println("<li><a href=\"computers?page=" + i + "&search=" + search + "\">" + i + "</a></li>");
					}
					else {
						out.println("<li><a href=\"computers?page=" + i + "\">" + i + "</a></li>");
					}
				}
			}
			
			// CURRENT PAGE
			out.println("<li class=\"active\"><a href=\"# \">" + currentPage + "</a></li>");
			
			// CURRENT PAGE + 2
			for(int i = currentPage + 1 ; i < currentPage + 3 ; i++ ) {
				if( i <= totalPages ) {
					if( search != null && !search.equals("")) {
						out.println("<li><a href=\"computers?page=" + i + "&search=" + search + "\">" + i + "</a></li>");
					}
					else {
						out.println("<li><a href=\"computers?page=" + i + "\">" + i + "</a></li>");
					}
				}
			}
			
			// NEXT PAGE
			if( currentPage == totalPages ) {
				out.println("<li class=\"disabled\"><a href=\"#\">&gt;</a></li>");
			} 
			else {
				if( search != null && !search.equals("")) {
					out.println("<li><a href=\"computers?page=" + (currentPage + 1) + "&search=" + search + "\">&gt;</a></li>");
				}
				else {
					out.println("<li><a href=\"computers?page=" + (currentPage + 1) + "\">&gt;</a></li>");
				}
			}
			
			// LAST PAGE
			if( currentPage + 2 != totalPages && currentPage != totalPages) {
				if( search != null && !search.equals("")) {
					out.println("<li><a href=\"computers?page=" + totalPages + "&search=" + search + "\">&raquo;</a></li>");
				}
				else {
					out.println("<li><a href=\"computers?page=" + totalPages + "\">&raquo;</a></li>");
				}
			}
			else {
				out.println("<li class=\"disabled\"><a href=\"computers?page=" + totalPages + "\">&raquo;</a></li>");
			}
			
			// END LIST
			out.println("</ul>");
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public String getSearch() {
		return search;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
}
