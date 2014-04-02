package com.excilys.computerdatabase.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.dao.CompanyDAO;
import com.excilys.computerdatabase.dao.ConnectionFactory;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.exception.SQLQueryFailException;

@Repository
public class CompanyDAOImpl implements CompanyDAO {
	
	private final static Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);
	
	@Autowired
	private ConnectionFactory connectionFactory;

	public Company getCompany(int id) {
		logger.debug("getCompany(" + id + ")");
		Connection connection = connectionFactory.getConnection();
		PreparedStatement getCompany = null;
		ResultSet rs = null;
		Company p = null;

		try {
			getCompany = connection.prepareStatement("SELECT * from company WHERE id = ?");
			getCompany.setInt(1, id);
			
			rs = getCompany.executeQuery();
			rs.next();
			
			p = new Company();
			p.setId(rs.getInt("id"));
			p.setName(rs.getString("name"));
			
			logger.debug("getCompany(" + id + ") successful");
		} catch (SQLException e) {
			throw new SQLQueryFailException(e);
		}
		finally {
			connectionFactory.closeObject(rs, getCompany);
		}
		return p;
	}

	public List<Company> getCompanies() {
		logger.debug("getCompanies()");
		Connection connection = connectionFactory.getConnection();

		PreparedStatement getCompanies = null;
		ResultSet rs = null;

		List<Company> companies = new ArrayList<Company>();
		try {
			getCompanies = connection.prepareStatement("SELECT id, name FROM company ORDER BY company.name");

			rs = getCompanies.executeQuery();
			
			while (rs.next()) {
				Company p = new Company();
				Integer id = rs.getInt("id");
				p.setId(id);
				p.setName(rs.getString("name"));
				companies.add(p);
			}

			logger.debug("getCompanies() successful");
		} catch (SQLException e) {
			throw new SQLQueryFailException(e);
		}
		finally {
			connectionFactory.closeObject(rs, getCompanies);
		}
		
		return companies;
	}
}
