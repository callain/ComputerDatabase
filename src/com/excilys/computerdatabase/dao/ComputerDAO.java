package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.computerdatabase.domain.Computer;

public class ComputerDAO {

	private static final String DAO_NAME = "computer";
	private static ComputerDAO ComputerDAO;

	private ComputerDAO() {
	}

	public static ComputerDAO getInstance() {
		if (ComputerDAO != null)
			return ComputerDAO;

		ComputerDAO = new ComputerDAO();
		return ComputerDAO;
	}

	public Computer getComputer(int id) {
		Connection connection = DAOFactory.getConnection();
		PreparedStatement getComputer = null;
		ResultSet rs = null;
		Computer p;

		try {
			getComputer = connection.prepareStatement("SELECT * from " + DAO_NAME + " WHERE id = ?");
			getComputer.setInt(1, id);

			rs = getComputer.executeQuery();
			rs.next();

			p = new Computer();
			p.setId(rs.getInt("id"));
			p.setName(rs.getString("name"));
			p.setIntroduced(rs.getTimestamp("introduced"));
			p.setDiscontinued(rs.getTimestamp("discontinued"));
			p.setCompanyId(rs.getInt("companyId"));

			return p;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (getComputer != null) {
					getComputer.close();
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

	public ArrayList<Computer> getComputers() {
		Connection connection = DAOFactory.getConnection();

		PreparedStatement getComputers = null;
		ResultSet rs = null;

		ArrayList<Computer> computerList = new ArrayList<Computer>();

		try {
			getComputers = connection.prepareStatement("SELECT * from "
					+ DAO_NAME);
			rs = getComputers.executeQuery();

			while (rs.next()) {
				Computer p = new Computer();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setIntroduced(rs.getTimestamp("introduced"));
				p.setDiscontinued(rs.getTimestamp("discontinued"));
				p.setCompanyId(rs.getInt("company_id"));
				computerList.add(p);
			}

			return computerList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (getComputers != null) {
					getComputers.close();
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

	public boolean addComputer(Computer p) {
		PreparedStatement insertComputer = null;
		Connection connection = DAOFactory.getConnection();

		try {
			insertComputer = connection.prepareStatement("INSERT INTO "
					+ DAO_NAME + " values(null,?,?,?,?)");
			insertComputer.setString(1, p.getName());
			insertComputer.setTimestamp(2, p.getIntroduced());
			insertComputer.setTimestamp(3, p.getDiscontinued());
			insertComputer.setInt(4, p.getCompanyId());

			return insertComputer.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (insertComputer != null) {
					insertComputer.close();
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
