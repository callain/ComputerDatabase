package com.excilys.computerdatabase.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.domain.Company;

public class CompanyWrapper
{
	private ArrayList<Company> companies;

	public CompanyWrapper()
	{

	}

	public CompanyWrapper(List<Company> companies)
	{
		this.setCompanies((ArrayList<Company>) companies);
	}

	public ArrayList<Company> getCompanies() 
	{
		return companies;
	}

	public void setCompanies(ArrayList<Company> companies)
	{
		this.companies = companies;
	}
}
