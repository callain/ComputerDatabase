package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.CompanyWrapper;

public interface CompanyService {
	public Company getCompany(int id);
	public CompanyWrapper getCompanies();
}