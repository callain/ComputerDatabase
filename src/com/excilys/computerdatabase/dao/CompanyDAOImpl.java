package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.domain.Company;

public enum CompanyDAOImpl implements CompanyDAO {
	
	INSTANCE;
	
	private final static Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);

	public Company getCompany(int id) throws SQLException {
		logger.debug("getCompany(" + id + ")");
		Connection connection = ConnectionFactory.INSTANCE.getConnection();
		PreparedStatement getCompany = null;
		ResultSet rs = null;
		Company p = null;

		getCompany = connection.prepareStatement("SELECT * from company WHERE id = ?");
		getCompany.setInt(1, id);

		rs = getCompany.executeQuery();
		rs.next();

		p = new Company();
		p.setId(rs.getInt("id"));
		p.setName(rs.getString("name"));
		
		rs.close();
		getCompany.close();
		
		logger.debug("getCompany(" + id + ") successful");
		return p;
	}

	public List<Company> getCompanies() throws SQLException {
		logger.debug("getCompanies()");
		Connection connection = ConnectionFactory.INSTANCE.getConnection();

		PreparedStatement getCompanies = null;
		ResultSet rs = null;

		List<Company> companies = new ArrayList<Company>();
		getCompanies = connection.prepareStatement("SELECT id, name FROM company ORDER BY company.name");
		rs = getCompanies.executeQuery();

		while (rs.next()) {
			Company p = new Company();
			Integer id = rs.getInt("id");
			p.setId(id);
			p.setName(rs.getString("name"));
			companies.add(p);
		}
		
		rs.close();
		getCompanies.close();
		
		logger.debug("getCompanies() successful");
		return companies;
	}
}
