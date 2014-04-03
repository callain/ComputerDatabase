package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.wrapper.CompanyWrapper;

public interface CompanyService {
	public Company getCompany(int id);
	public CompanyWrapper getCompanies();
}
