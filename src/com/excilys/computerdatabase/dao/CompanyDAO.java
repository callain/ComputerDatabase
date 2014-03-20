package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.excilys.computerdatabase.domain.Company;

public class CompanyDAO {
	private static final String DAO_NAME = "company";
	private static CompanyDAO CompanyDAO;

	private CompanyDAO() {
	}

	public static CompanyDAO getInstance() {
		if (CompanyDAO != null)
			return CompanyDAO;

		CompanyDAO = new CompanyDAO();
		return CompanyDAO;
	}

	public Company getCompany(int id) {
		Connection connection = DAOFactory.getConnection();
		PreparedStatement getCompany = null;
		ResultSet rs = null;
		Company p;

		try {
			getCompany = connection.prepareStatement("SELECT * from " 
					+ DAO_NAME + " WHERE id = ?");
			getCompany.setInt(1, id);

			rs = getCompany.executeQuery();
			rs.next();

			p = new Company();
			p.setId(rs.getInt("id"));
			p.setName(rs.getString("name"));

			return p;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (getCompany != null) {
					getCompany.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public Map<Integer, Company> getCompanies() {
		Connection connection = DAOFactory.getConnection();

		PreparedStatement getCompanys = null;
		ResultSet rs = null;

		Map<Integer, Company> companies = new HashMap<Integer, Company>();
		try {
			getCompanys = connection.prepareStatement("SELECT * from "
					+ DAO_NAME);
			rs = getCompanys.executeQuery();

			while (rs.next()) {
				Company p = new Company();
				Integer id = rs.getInt("id");
				p.setId(id);
				p.setName(rs.getString("name"));
				companies.put(id, p);
			}

			return companies;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (getCompanys != null) {
					getCompanys.close();
				}

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public boolean addCompany(Company p) {
		PreparedStatement insertCompany = null;
		Connection connection = DAOFactory.getConnection();

		try {
			insertCompany = connection.prepareStatement("INSERT INTO "
					+ DAO_NAME + " values(null,?)");
			insertCompany.setString(1, p.getName());

			return insertCompany.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (insertCompany != null) {
					insertCompany.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}
}
