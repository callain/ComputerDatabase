package com.excilys.computerdatabase.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.excilys.computerdatabase.domain.Company;

public class CompanyExtractor implements ResultSetExtractor<Company>
{
	@Override
	public Company extractData(ResultSet rs) throws SQLException, DataAccessException {
		Company company = new Company();
		company.setId(rs.getInt(1));
		company.setName(rs.getString(2));

		return company;
	}

}
