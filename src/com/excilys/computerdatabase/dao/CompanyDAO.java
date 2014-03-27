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

public class CompanyDAO {
	
	final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	private static CompanyDAO companyDAO;

	private CompanyDAO() {
	}

	public static CompanyDAO getInstance() {
		if (companyDAO != null)
			return companyDAO;

		companyDAO = new CompanyDAO();
		return companyDAO;
	}

	public Company getCompany(int id) {
		logger.debug("getCompany(" + id + ")");
		Connection connection = DAOFactory.INSTANCE.getConnection();
		PreparedStatement getCompany = null;
		ResultSet rs = null;
		Company p;

		try {
			getCompany = connection.prepareStatement("SELECT * from company WHERE id = ?");
			getCompany.setInt(1, id);

			rs = getCompany.executeQuery();
			rs.next();

			p = new Company();
			p.setId(rs.getInt("id"));
			p.setName(rs.getString("name"));
			
			logger.debug("getCompany(" + id + ") successful");
			return p;
		} catch (SQLException e) {
			logger.warn("getCompany(" + id + ") failed with: " + e.getMessage());
		} finally {
			DAOFactory.INSTANCE.closeObject(connection, rs, getCompany);
		}

		return null;
	}

	public List<Company> getCompanies() {
		logger.debug("getCompanies()");
		Connection connection = DAOFactory.INSTANCE.getConnection();

		PreparedStatement getCompanies = null;
		ResultSet rs = null;

		List<Company> companies = new ArrayList<Company>();
		try {
			getCompanies = connection.prepareStatement("SELECT id, name FROM company");
			rs = getCompanies.executeQuery();

			while (rs.next()) {
				Company p = new Company();
				Integer id = rs.getInt("id");
				p.setId(id);
				p.setName(rs.getString("name"));
				companies.add(p);
			}
			logger.debug("getCompanies() successful");
			return companies;
		} catch (SQLException e) {
			logger.warn("getCompanies() failed with: " + e.getMessage());
		} finally {
			DAOFactory.INSTANCE.closeObject(connection, rs, getCompanies);
		}

		return null;
	}

	public boolean addCompany(Company p) {
		logger.debug("addCompany(" + p + ")");
		PreparedStatement insertCompany = null;
		Connection connection = DAOFactory.INSTANCE.getConnection();

		try {
			insertCompany = connection.prepareStatement("INSERT INTO company values(null,?)");
			insertCompany.setString(1, p.getName());

			logger.debug("addCompany(" + p + ") successful");
			return insertCompany.execute();
		} catch (SQLException e) {
			logger.warn("addCompany() failed with: " + e.getMessage());
		} finally {
			DAOFactory.INSTANCE.closeObject(connection, null, insertCompany);
		}

		return false;
	}
}
