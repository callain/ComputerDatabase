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

	private static ComputerDAO computerDAO;

	private ComputerDAO() {
	}

	public static ComputerDAO getInstance() {
		if (computerDAO != null)
			return computerDAO;

		computerDAO = new ComputerDAO();
		return computerDAO;
	}

	public Computer getComputer(int id) {
		logger.debug("getComputer(" + id + ")");
		
		Connection connection = DAOFactory.INSTANCE.getConnection();

		PreparedStatement getComputer = null;
		ResultSet rs = null;
		Computer p = null;

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name ");
		sb.append("FROM computer ");
		sb.append("LEFT JOIN company ON computer.company_id = company.id ");
		sb.append("WHERE computer.id = ? ");

		try {
			connection.setAutoCommit(false);
			
			getComputer = connection.prepareStatement(sb.toString());			
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
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("getComputer(" + id + ") failed with: " + e.getMessage());
			try {
				logger.error("getComputer(" + id + ") Connection is being rollback()");
				connection.rollback();
			} catch (SQLException exception) {
				logger.error("getComputer(" + id + ") failed with: " + exception.getMessage());
			}
		} finally {
			DAOFactory.INSTANCE.closeObject(connection, rs, getComputer);
		}

		logger.debug("getComputer(" + id + ") successful");
		return p;
	}
	
	public ArrayList<Computer> getComputers(QueryBuilder qb) {
		logger.debug("getComputers(" + qb + ")");
		Connection connection = DAOFactory.INSTANCE.getConnection();
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name ");
		sb.append("FROM computer ");
		sb.append("LEFT JOIN company ON computer.company_id = company.id ");
		sb.append("WHERE computer.name LIKE ? OR company.name LIKE ? ");
		
		String field = (qb.getField() == null)? ComputerField.NAME.getName() : qb.getField();
		if( qb.getDirection() ) {
			sb.append(String.format("ORDER BY %s DESC ", field));
		}
		else {
			sb.append(String.format("ORDER BY %s ", field));
		}
		
		sb.append("LIMIT ?,?");
		
		PreparedStatement getComputers = null;
		ResultSet rs = null;
		ArrayList<Computer> computerList = new ArrayList<Computer>();
		
		try {
			getComputers = connection.prepareStatement(sb.toString());
			connection.setAutoCommit(false);
			
			String search = (qb.getSearch() == null)? "" : qb.getSearch();

			getComputers.setString(1, "%" + search + "%");
			getComputers.setString(2, "%" + search + "%");

			getComputers.setInt(3, qb.getOffset());
			getComputers.setInt(4, qb.getNbRows());

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
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("getComputers() failed with: " + e.getMessage());
			try{
				logger.error("getComputers() Connection is being rollback()");
				connection.rollback();
			}
			catch(SQLException exception) {
				logger.error("getComputers() failed with: " + exception.getMessage());
			}
		} finally {
			DAOFactory.INSTANCE.closeObject(connection, rs, getComputers);
		}

		logger.debug("getComputers() successful");
		return computerList;
	}

	public boolean addComputer(Computer c) {
		logger.debug("addComputer(" + c + ")");
		PreparedStatement insertComputer = null;
		Connection connection = DAOFactory.INSTANCE.getConnection();
		boolean results = false;
		
		try {
			connection.setAutoCommit(false);
			
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
			
			results = insertComputer.execute();
			connection.commit();
			LogDAO.getInstance().addLog("Computer added with id: " + c.getId());
		} catch (SQLException e) {
			logger.error("addComputer(" + c + ") failed with: " + e.getMessage());
			try {
				logger.error("addComputer(" + c + ") Connection is being rollback()");
				connection.rollback();
			} catch (SQLException exception) {
				logger.error("addComputer(" + c + ") failed with: "  + exception.getMessage());
			}
		} finally {
			DAOFactory.INSTANCE.closeObject(connection, null, insertComputer);
		}

		logger.debug("addComputer(" + c + ") successful");
		return results;
	}
	
	public int updateComputer(Computer c) {
		logger.debug("updateComputer(" + c + ")");
		Connection connection = DAOFactory.INSTANCE.getConnection();

		PreparedStatement updateComputer = null;

		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? ");
		sb.append("WHERE id = ? ");

		int results = 0;
		try {
			connection.setAutoCommit(false);
			updateComputer = connection.prepareStatement(sb.toString());
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
			
			results = updateComputer.executeUpdate();
			connection.commit();
		} catch(SQLException e) {
			logger.error("updateComputer(" + c + ") failed with: " + e.getMessage());
			try {
				logger.error("updateComputer(" + c + ") Connection is being rollback()");
				connection.rollback();
			} catch (SQLException exception) {
				logger.error("updateComputer(" + c + ") failed with: " + e.getMessage());
			}
		} finally {
			DAOFactory.INSTANCE.closeObject(connection, null, updateComputer);
		}
	
		logger.debug("updateComputer(" + c + ") successful");	
		return results;
	}
	

	public boolean deleteComputer(int id) {
		logger.debug("deleteComputer(" + id + ")");
		PreparedStatement deleteComputer = null;
		Connection connection = DAOFactory.INSTANCE.getConnection();
		
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM computer ");
		sb.append("WHERE id = ?");
		
		boolean returnz = false;
		try {
			deleteComputer = connection.prepareStatement(sb.toString());
			deleteComputer.setInt(1, id);
			returnz = deleteComputer.execute();
		}
		catch(SQLException e) {
			logger.error("deleteComputer(" + id + ") failed with: " + e.getMessage());
			try {
				logger.error("deleteComputer(" + id + ") Connection is being rollback()");
				connection.rollback();
			}
			catch(SQLException exception) {
				logger.error("deleteComputer(" + id + ") failed with: " + e.getMessage());	
			}
		}
		finally {
			DAOFactory.INSTANCE.closeObject(connection, null, deleteComputer);
		}
		
		logger.debug("deleteComputer(" + id + ") successful");
		return returnz;
	}

	public int getTotalComputers(QueryBuilder qb) {
		logger.debug("getTotalComputers(" + qb + ")");
		Connection connection = DAOFactory.INSTANCE.getConnection();
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT count(computer.id) ");
		sb.append("FROM computer ");
		sb.append("LEFT JOIN company ON computer.company_id = company.id ");
		sb.append("WHERE computer.name LIKE ? OR company.name LIKE ? ");
		
		String field = (qb.getField() == null)? ComputerField.NAME.getName() : qb.getField();
		if( qb.getDirection() ) {
			sb.append(String.format("ORDER BY %s DESC ", field));
		}
		else {
			sb.append(String.format("ORDER BY %s ", field));
		}

		PreparedStatement getComputers = null;
		ResultSet rs = null;
		int results = 0;
		try {
			connection.setAutoCommit(false);
			getComputers = connection.prepareStatement(sb.toString());
			
			String search = (qb.getSearch() == null)? "" : qb.getSearch();

			getComputers.setString(1, "%" + search + "%");
			getComputers.setString(2, "%" + search + "%");

			rs = getComputers.executeQuery();
			rs.next();
			results =  rs.getInt(1);
			
			connection.commit();
		} catch (SQLException e) {
			logger.error("getTotalComputers() failed with: " + e.getMessage());
			try {
				logger.error("getTotalComputers() Connection is being rollback()");
				connection.rollback();
			} catch (SQLException exception) {
				logger.error("getTotalComputers() failed with: " + e.getMessage());
			}
		} finally {
			DAOFactory.INSTANCE.closeObject(connection, rs, getComputers);
		}

		logger.debug("getTotalComputers() successful");
		return results;
	}
}
