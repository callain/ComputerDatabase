package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
		Computer p = null;

		try {
			getComputer = connection.prepareStatement(
					"SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name "
			+ 		"FROM computer "
			+		"LEFT JOIN company ON computer.company_id = company.id "
			+ 		"WHERE computer.id = ?"
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
			
		} catch (SQLException e) {
			logger.warn("getComputer(" + id + ") failed with: " + e.getMessage());
			return null;
		} finally {
			DAOFactory.closeObject(connection, rs, getComputer);
		}

		logger.debug("getComputer(" + id + ") successful");
		return p;
	}

	public ArrayList<Computer> getComputers(int offset, int nbRows) {
		logger.debug("getComputers()");
		Connection connection = DAOFactory.getConnection();

		PreparedStatement getComputers = null;
		ResultSet rs = null;

		ArrayList<Computer> computerList = new ArrayList<Computer>();

		try {
			getComputers = connection.prepareStatement(
						"SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name "
					+ 	"FROM computer "
					+ 	"LEFT JOIN company ON computer.company_id = company.id "
					+	"LIMIT ?,?");
			
			getComputers.setInt(1, offset);
			getComputers.setInt(2, nbRows);
			
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
		} catch (SQLException e) {
			logger.warn("getComputers() failed with: " + e.getMessage());
			return null;
		} finally {
			DAOFactory.closeObject(connection, rs, getComputers);
		}

		logger.debug("getComputers() successful");
		return computerList;
	}
	
	public int getTotalComputers() {
		logger.debug("getTotalComputers()");
		
		PreparedStatement getTotalComputers = null;
		Connection connection = DAOFactory.getConnection();
		
		ResultSet rs = null;
		int results = 0;
		try {
			getTotalComputers = connection.prepareStatement("SELECT count(id) FROM computer");
			rs = getTotalComputers.executeQuery();
			rs.next();
			
			results =  rs.getInt(1);
		}
		catch(SQLException e) {
			logger.warn("getComputers() failed with: " + e.getMessage());
			return 0;
		}
		finally {
			DAOFactory.closeObject(connection, rs, getTotalComputers);
		}
		
		return results;
	}

	public boolean addComputer(Computer c) {
		logger.debug("addComputer(" + c + ")");
		PreparedStatement insertComputer = null;
		Connection connection = DAOFactory.getConnection();
		boolean returnz = false;
		
		try {
			insertComputer = connection.prepareStatement("INSERT INTO computer values(null,?,?,?,?)");
			insertComputer.setString(1, c.getName());
			insertComputer.setTimestamp(2, c.getIntroduced());
			insertComputer.setTimestamp(3, c.getDiscontinued());
			if( c.getCompany() == null ) {
				insertComputer.setNull(4, Types.NULL);
			}
			else {
				insertComputer.setInt(4, c.getCompany().getId());
			}

			returnz = insertComputer.execute();
		} catch (SQLException e) {
			logger.warn("addComputer(" + c + ") failed with: " +e.getMessage());
			return false;
		} finally {
			DAOFactory.closeObject(connection, null, insertComputer);
		}

		logger.debug("addComputer(" + c + ") successful");
		return returnz;
	}
	
	public int updateComputer(Computer c) {
		logger.debug("updateComputer(" + c + ")");
		PreparedStatement updateComputer = null;
		Connection connection = DAOFactory.getConnection();
		int returnz = 0;
		try {
			updateComputer = connection.prepareStatement(
						"UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? "
					+ 	"WHERE id = ?");
			
			updateComputer.setString(1, c.getName());
			updateComputer.setTimestamp(2, c.getIntroduced());
			updateComputer.setTimestamp(3, c.getDiscontinued());
			
			if( c.getCompany() != null ) {
				updateComputer.setInt(4, c.getCompany().getId());
			}
			else {
				updateComputer.setNull(4, Types.NULL);
			}
			
			updateComputer.setInt(5, c.getId());
			
			returnz = updateComputer.executeUpdate();
		} catch(SQLException e) {
			logger.warn("updateComputer(" + c + ") failed with: " + e.getMessage());
			return 0;
		} finally {
			DAOFactory.closeObject(connection, null, updateComputer);
		}
	
		logger.debug("updateComputer(" + c + ") successful");	
		return returnz;
	}
	

	public boolean deleteComputer(int id) {
		logger.debug("deleteComputer(" + id + ")");
		PreparedStatement deleteComputer = null;
		Connection connection = DAOFactory.getConnection();
		boolean returnz = false;
		try {
			deleteComputer = connection.prepareStatement(
					"DELETE FROM computer "
				+	"WHERE id = ?");
			deleteComputer.setInt(1, id);
			returnz = deleteComputer.execute();
		}
		catch(SQLException e) {
			logger.warn("deleteComputer(" + id + ") failed with: " + e.getMessage());
			return false;
		}
		finally {
			DAOFactory.closeObject(connection, null, deleteComputer);
		}
		
		logger.debug("deleteComputer(" + id + ") successful");
		return returnz;
	}
	
	public ArrayList<Computer> search(String name, int offset, int nbRows) {
		logger.debug("search(" + name + ")");
		Connection connection = DAOFactory.getConnection();

		PreparedStatement getComputers = null;
		ResultSet rs = null;

		ArrayList<Computer> computerList = new ArrayList<Computer>();

		try {
			getComputers = connection.prepareStatement(
						"SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name "
					+ 	"FROM computer "
					+ 	"LEFT JOIN company ON computer.company_id = company.id "
					+	"WHERE computer.name LIKE ? "
					+	"OR company.name LIKE ? "
					+ 	"LIMIT ?,?");
			
			getComputers.setString(1, "%" + name + "%");
			getComputers.setString(2, "%" + name + "%");
			getComputers.setInt(3, offset);
			getComputers.setInt(4, nbRows);
			
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
			
		} catch (SQLException e) {
			logger.debug("search(" + name + ") failed with: " + e.getMessage());
			return null;
		} finally {
			DAOFactory.closeObject(connection, rs, getComputers);
		}

		logger.debug("search(" + name + ") successful");
		return computerList;
	}
}
