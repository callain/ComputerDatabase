package com.excilys.computerdatabase.domain;

import java.util.ArrayList;
import java.util.List;

public class CompanyWrapper {
	private ArrayList<Company> companies;

	public CompanyWrapper() {

	}

	public CompanyWrapper(List<Company> companies) {
		this.setCompanys((ArrayList<Company>) companies);
	}

	public ArrayList<Company> getCompanies() {
		return companies;
	}

	public void setCompanys(ArrayList<Company> companies) {
		this.companies = companies;
	}
}
