package com.excilys.computerdatabase.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.dao.ComputerField;
import com.excilys.computerdatabase.dao.ConnectionFactory;
import com.excilys.computerdatabase.dao.QueryBuilder;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.exception.SQLQueryFailException;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class ComputerDAOImpl implements ComputerDAO
{
	private final Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
	
	@Autowired
	private ConnectionFactory connectionFactory;
	
	@Autowired
	private BoneCPDataSource boneCP;
	
	@Override
	public Computer getComputer(int id) throws SQLQueryFailException
	{
		logger.debug("getComputer(" + id + ")");
		
		Connection connection = DataSourceUtils.getConnection(boneCP);
		
		PreparedStatement getComputer = null;
		ResultSet rs = null;
		Computer p = null;

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name ");
		sb.append("FROM computer ");
		sb.append("LEFT JOIN company ON computer.company_id = company.id ");
		sb.append("WHERE computer.id = ? ");

		try {
			getComputer = connection.prepareStatement(sb.toString());
			getComputer.setInt(1, id);
			
			rs = getComputer.executeQuery();
			rs.next();
			
			p = new Computer();
			p.setId(rs.getInt("id"));
			p.setName(rs.getString("name"));
			if( rs.getTimestamp(3) != null ) p.setIntroduced(new DateTime(rs.getTimestamp("introduced")));
			else p.setIntroduced(null);
			if( rs.getTimestamp(4) != null ) p.setDiscontinued(new DateTime(rs.getTimestamp("discontinued")));
			else p.setDiscontinued(null);
			Company company = new Company();
			company.setId(rs.getInt(5));
			company.setName(rs.getString(6));
			p.setCompany(company);
		} catch (SQLException e) {
			throw new SQLQueryFailException(e);
		}			
		finally
		{
			connectionFactory.closeObject(rs, getComputer);
		}
		
		logger.debug("getComputer(" + id + ") successful");
		return p;
	}
	
	@Override
	public List<Computer> getComputers(QueryBuilder qb) throws SQLQueryFailException
	{
		logger.debug("getComputers(" + qb + ")");
		
		Connection connection = DataSourceUtils.getConnection(boneCP);
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name ");
		sb.append("FROM computer ");
		sb.append("LEFT JOIN company ON computer.company_id = company.id ");
		sb.append("WHERE computer.name LIKE ? OR company.name LIKE ? ");
		
		if( qb.getDirection() ) sb.append(String.format("ORDER BY %s DESC ", qb.getField()));
		else sb.append(String.format("ORDER BY %s ", qb.getField()));
		
		sb.append("LIMIT ?,?");
		
		PreparedStatement getComputers = null;
		ResultSet rs = null;
		ArrayList<Computer> computerList = new ArrayList<Computer>();
		
		try
		{
			getComputers = connection.prepareStatement(sb.toString());
			String search = (qb.getSearch() == null)? "" : qb.getSearch();
			getComputers.setString(1, "%" + search + "%");
			getComputers.setString(2, "%" + search + "%");
			
			getComputers.setInt(3, qb.getOffset());
			getComputers.setInt(4, qb.getNbRows());
			
			rs = getComputers.executeQuery();
			
			while (rs.next())
			{
				Computer p = new Computer();
				p.setId(rs.getInt(1));
				p.setName(rs.getString(2));
				if( rs.getTimestamp(3) != null ) p.setIntroduced(new DateTime(rs.getTimestamp(3)));
				else p.setIntroduced(null);
				if( rs.getTimestamp(4) != null ) p.setDiscontinued(new DateTime(rs.getTimestamp(4)));
				else p.setDiscontinued(null);
				Company company = new Company();
				company.setId(rs.getInt(5));
				company.setName(rs.getString(6));
				p.setCompany(company);
				computerList.add(p);
			}
		}
		catch (SQLException e)
		{
			throw new SQLQueryFailException(e);
		}
		finally
		{
			connectionFactory.closeObject(rs, getComputers);
		}

		logger.debug("getComputers() successful");
		return computerList;
	}

	@Override
	public int addComputer(Computer c) throws SQLQueryFailException
	{
		logger.debug("addComputer(" + c + ")");
		
		Connection connection = DataSourceUtils.getConnection(boneCP);		
		
		PreparedStatement insertComputer = null;
		int results = 0;
		ResultSet rs = null;
		try
		{
			insertComputer = connection.prepareStatement("INSERT INTO computer values(null,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			insertComputer.setString(1, c.getName());
			
			if( c.getIntroduced() != null ) insertComputer.setTimestamp(2, new Timestamp(c.getIntroduced().getMillis()));
			else insertComputer.setNull(2, Types.NULL);
			
			if( c.getDiscontinued() != null ) insertComputer.setTimestamp(3, new Timestamp(c.getDiscontinued().getMillis()));
			else insertComputer.setNull(3, Types.NULL);
			
			if( c.getCompany() == null ) insertComputer.setNull(4, Types.NULL);
			else insertComputer.setInt(4, c.getCompany().getId());
			
			insertComputer.execute();
			rs = insertComputer.getGeneratedKeys();
			rs.next();
			results = rs.getInt(1);
		}
		catch (SQLException e)
		{
			throw new SQLQueryFailException(e);
		}
		finally
		{
			connectionFactory.closeObject(rs, insertComputer);
		}

		logger.debug("addComputer(" + c + ") successful");
		return results;
	}
	
	@Override
	public int updateComputer(Computer c) throws SQLQueryFailException
	{
		logger.debug("updateComputer(" + c + ")");

		Connection connection = DataSourceUtils.getConnection(boneCP);
		
		PreparedStatement updateComputer = null;

		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? ");
		sb.append("WHERE id = ? ");
		
		int results = 0;

		try
		{
			updateComputer = connection.prepareStatement(sb.toString());
			updateComputer.setString(1, c.getName());
			
			if( c.getIntroduced() != null ) updateComputer.setTimestamp(2, new Timestamp(c.getIntroduced().getMillis()));
			else updateComputer.setNull(2, Types.NULL);
			
			if( c.getDiscontinued() != null ) updateComputer.setTimestamp(3, new Timestamp(c.getDiscontinued().getMillis()));
			else updateComputer.setNull(3, Types.NULL);
			
			if( c.getCompany() != null ) updateComputer.setInt(4, c.getCompany().getId());
			else updateComputer.setNull(4, Types.NULL);
			
			updateComputer.setInt(5, c.getId());
			results = updateComputer.executeUpdate();
			updateComputer.close();
			
			logger.debug("updateComputer(" + c + ") successful");	
		}
		catch (SQLException e)
		{
			throw new SQLQueryFailException(e);
		}
		finally
		{
			connectionFactory.closeObject(null, updateComputer);
		}
		
		return results;
	}
	
	@Override
	public boolean deleteComputer(int id) throws SQLQueryFailException
	{
		logger.debug("deleteComputer(" + id + ")");

		Connection connection = DataSourceUtils.getConnection(boneCP);

		PreparedStatement deleteComputer = null;
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM computer ");
		sb.append("WHERE id = ?");
		
		boolean returnz = false;
		try
		{
			deleteComputer = connection.prepareStatement(sb.toString());
			deleteComputer.setInt(1, id);
			returnz = deleteComputer.execute();
			
			deleteComputer.close();
		}
		catch (SQLException e)
		{
			throw new SQLQueryFailException(e);
		}
		finally
		{
			connectionFactory.closeObject(null, deleteComputer);
		}
		
		logger.debug("deleteComputer(" + id + ") successful");
		return returnz;
	}

	@Override
	public int getTotalComputers(QueryBuilder qb) throws SQLQueryFailException
	{
		logger.debug("getTotalComputers(" + qb + ")");
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT count(computer.id) ");
		sb.append("FROM computer ");
		sb.append("LEFT JOIN company ON computer.company_id = company.id ");
		sb.append("WHERE computer.name LIKE ? OR company.name LIKE ? ");
		
		String field = (qb.getField() == null)? ComputerField.NAME.getName() : qb.getField();
		if( qb.getDirection() )
		{
			sb.append(String.format("ORDER BY %s DESC ", field));
		}
		else
		{
			sb.append(String.format("ORDER BY %s ", field));
		}

		PreparedStatement getComputers = null;
		ResultSet rs = null;
		int results = 0;
		
		Connection connection = DataSourceUtils.getConnection(boneCP);
		try
		{
			getComputers = connection.prepareStatement(sb.toString());
			String search = (qb.getSearch() == null)? "" : qb.getSearch();
			getComputers.setString(1, "%" + search + "%");
			getComputers.setString(2, "%" + search + "%");
			
			rs = getComputers.executeQuery();
			rs.next();
			results =  rs.getInt(1);
		}
		catch (SQLException e)
		{
			throw new SQLQueryFailException(e);
		}
		finally
		{
			connectionFactory.closeObject(rs, getComputers);
		}
		
		logger.debug("getTotalComputers() successful");
		return results;
	}
}
