package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;

public class ComputerDAO {
	final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

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
		logger.debug("getComputer(" + id + ")");
		Connection connection = DAOFactory.getConnection();
		PreparedStatement getComputer = null;
		ResultSet rs = null;
		Computer p;

		try {
			getComputer = connection.prepareStatement(
					"SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name"
			+ 		"FROM computer, company"
			+		"LEFT JOIN company ON computer.company_id = company.id"
			+ 		"WHERE id = ?"
			);
			
			getComputer.setInt(1, id);

			rs = getComputer.executeQuery();
			rs.next();

			p = new Computer();
			p.setId(rs.getInt("id"));
			p.setName(rs.getString("name"));
			p.setIntroduced(rs.getTimestamp("introduced"));
			p.setDiscontinued(rs.getTimestamp("discontinued"));
			Company company = new Company();
			company.setId(rs.getInt(5));
			company.setName(rs.getString(6));
			p.setCompany(company);
			
			logger.debug("getComputer(" + id + ") successful");
			
			return p;
		} catch (SQLException e) {
			logger.warn("getComputer(" + id + ") failed with: " + e.getMessage());
		} finally {
			DAOFactory.closeObject(connection, rs, getComputer);
		}

		return null;
	}

	public ArrayList<Computer> getComputers() {
		logger.debug("getComputers()");
		Connection connection = DAOFactory.getConnection();

		PreparedStatement getComputers = null;
		ResultSet rs = null;

		ArrayList<Computer> computerList = new ArrayList<Computer>();

		try {
			getComputers = connection.prepareStatement("SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id");
			rs = getComputers.executeQuery();

			while (rs.next()) {
				Computer p = new Computer();
				p.setId(rs.getInt(1));
				p.setName(rs.getString(2));
				p.setIntroduced(rs.getTimestamp(3));
				p.setDiscontinued(rs.getTimestamp(4));
				Company company = new Company();
				company.setId(rs.getInt(5));
				company.setName(rs.getString(6));
				p.setCompany(company);
				computerList.add(p);
			}
			
			logger.debug("getComputers() successful");

			return computerList;
		} catch (SQLException e) {
			logger.debug("getComputers() failed with: " + e.getMessage());
		} finally {
			DAOFactory.closeObject(connection, rs, getComputers);
		}

		return null;
	}

	public boolean addComputer(Computer c) {
		logger.debug("addComputer(" + c + ")");
		PreparedStatement insertComputer = null;
		Connection connection = DAOFactory.getConnection();

		try {
			insertComputer = connection.prepareStatement("INSERT INTO computer values(null,?,?,?,?)");
			insertComputer.setString(1, c.getName());
			insertComputer.setTimestamp(2, c.getIntroduced());
			insertComputer.setTimestamp(3, c.getDiscontinued());
			insertComputer.setInt(4, c.getCompany().getId());

			logger.debug("addComputer(" + c + ") successfull");
			return insertComputer.execute();
		} catch (SQLException e) {
			logger.warn("addComputer(" + c + ") failed with: " +e.getMessage());
		} finally {
			DAOFactory.closeObject(connection, null, insertComputer);
		}

		return false;
	}
	
	public int updateComputer(Computer c) {
		logger.debug("updateComputer(" + c + ")");
		PreparedStatement updateComputer = null;
		Connection connection = DAOFactory.getConnection();
		
		try {
			updateComputer = connection.prepareStatement("UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ?");
			updateComputer.setString(1, c.getName());
			updateComputer.setTimestamp(2, c.getIntroduced());
			updateComputer.setTimestamp(3, c.getDiscontinued());
			updateComputer.setInt(4, c.getCompany().getId());
			logger.debug("updateComputer(" + c + ") successful");	
			return updateComputer.executeUpdate();
		} catch(SQLException e) {
			logger.warn("updateComputer(" + c + ") failed with: " + e.getMessage());
		} finally {
			DAOFactory.closeObject(connection, null, updateComputer);
		}
	
		return 0;
	}
}
