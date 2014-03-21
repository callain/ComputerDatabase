package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.dao.CompanyDAO;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.CompanyWrapper;

public class CompanyServiceImpl implements CompanyService {

	private CompanyDAO companyDAO;
	
	public CompanyServiceImpl() {
		companyDAO = CompanyDAO.getInstance();
	}
	
	@Override
	public Company getCompany(int id) {
		return 	companyDAO.getCompany(id);
	}

	@Override
	public boolean addCompany(Company c) {
		return companyDAO.addCompany(c);
	}

	@Override
	public CompanyWrapper getCompanies() {
		return new CompanyWrapper(companyDAO.getCompanies());
	}
}
