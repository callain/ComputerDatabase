package com.excilys.computerdatabase.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.extractor.CompanyExtractor;

public class CompanyRowMapper implements RowMapper<Company> {
	@Override
	public Company mapRow(ResultSet rs, int line) throws SQLException {
		CompanyExtractor cExtractor = new CompanyExtractor();
		return cExtractor.extractData(rs);
	}
}