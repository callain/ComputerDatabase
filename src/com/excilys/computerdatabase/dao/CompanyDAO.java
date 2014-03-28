package com.excilys.computerdatabase.dao;

import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.domain.Company;

public interface CompanyDAO {
	public Company getCompany(int id) throws SQLException;
	public List<Company> getCompanies() throws SQLException;
}
