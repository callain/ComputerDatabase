package com.excilys.computerdatabase.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.exception.SQLQueryFailException;
import com.excilys.computerdatabase.persistence.ComputerDAO;
import com.excilys.computerdatabase.persistence.ComputerField;
import com.excilys.computerdatabase.persistence.QueryBuilder;
import com.excilys.computerdatabase.rowmapper.ComputerRowMapper;

@Repository
public class ComputerDAOImpl implements ComputerDAO
{
	private final Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Override
	public Computer getComputer(int id) throws SQLQueryFailException
	{
		logger.debug("getComputer(" + id + ")");
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name ");
		sb.append("FROM computer ");
		sb.append("LEFT JOIN company ON computer.company_id = company.id ");
		sb.append("WHERE computer.id = ? ");

		List<Computer> computerList;
		try
		{
			computerList = jdbc.query(sb.toString(), new Object[] { id }, new ComputerRowMapper() );
		}
		catch (DataAccessException e)
		{
			throw new SQLQueryFailException(e);
		}			
		
		logger.debug("getComputer(" + id + ") successful");

		if( !computerList.isEmpty() ) return computerList.get(0);
		else return null;
	}
	
	@Override
	public List<Computer> getComputers(QueryBuilder qb) throws SQLQueryFailException
	{
		logger.debug("getComputers(" + qb + ")");
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name ");
		sb.append("FROM computer ");
		sb.append("LEFT JOIN company ON computer.company_id = company.id ");
		sb.append("WHERE computer.name LIKE ? OR company.name LIKE ? ");
		
		if( qb.getDirection() ) sb.append(String.format("ORDER BY %s DESC ", qb.getField()));
		else sb.append(String.format("ORDER BY %s ", qb.getField()));
		
		sb.append("LIMIT ?,?");
		
		List<Computer> computerList = new ArrayList<Computer>();
		
		try
		{
			String search = (qb.getSearch() == null)? "" : qb.getSearch();
			
			Object[] parameters = new Object[] { "%" + search + "%", "%" + search + "%", qb.getOffset(), qb.getNbRows() };
			computerList = jdbc.query(sb.toString(), parameters, new ComputerRowMapper() );
		}
		catch (DataAccessException e)
		{
			throw new SQLQueryFailException(e);
		}

		logger.debug("getComputers() successful");
		return computerList;
	}

	@Override
	public int addComputer(final Computer c) throws SQLQueryFailException
	{
		logger.debug("addComputer(" + c + ")");
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(new PreparedStatementCreator()
		{
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException
			{
				PreparedStatement ps = connection.prepareStatement("INSERT INTO computer values(null,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, c.getName());
				
				if( c.getIntroduced() != null ) ps.setTimestamp(2,new Timestamp(c.getIntroduced().getMillis()));
				else ps.setNull(2, Types.NULL);
				
				if( c.getDiscontinued() != null ) ps.setTimestamp(3, new Timestamp(c.getDiscontinued().getMillis()));
				else ps.setNull(3, Types.NULL);
				
				if( c.getCompany() != null ) ps.setInt(4, c.getCompany().getId());
				else ps.setNull(4, Types.NULL);

	            return ps;
			}
		}, keyHolder);

		logger.debug("addComputer(" + c + ") successful");
		return keyHolder.getKey().intValue();
	}
	
	@Override
	public int updateComputer(Computer c) throws SQLQueryFailException
	{
		logger.debug("updateComputer(" + c + ")");

		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? ");
		sb.append("WHERE id = ? ");
		
		int results = 0;

		Object[] parameters = new Object[5];
		jdbc.update(sb.toString(), parameters);
		parameters[0] = c.getName();
		
		if( c.getIntroduced() != null ) parameters[1] = new Timestamp(c.getIntroduced().getMillis());
		else parameters[1] =  Types.NULL;
		
		if( c.getDiscontinued() != null ) parameters[2] = new Timestamp(c.getDiscontinued().getMillis());
		else parameters[2] = Types.NULL;
		
		if( c.getCompany() != null ) parameters[3] = c.getCompany().getId();
		else parameters[3] = Types.NULL;
		
		parameters[4] = c.getId();
		
		jdbc.update(sb.toString(), parameters);
		
		logger.debug("updateComputer(" + c + ") successful");	
		return results;
	}
	
	@Override
	public boolean deleteComputer(int id) throws SQLQueryFailException
	{
		logger.debug("deleteComputer(" + id + ")");

		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM computer ");
		sb.append("WHERE id = ?");

		int rowsAffected = jdbc.update(sb.toString(), new Object[] { id });
		
		logger.debug("deleteComputer(" + id + ") successful");
		return rowsAffected >= 1;
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

		String search = (qb.getSearch() == null)? "" : qb.getSearch();
		Object[] parameters = new Object[] { "%" + search + "%", "%" + search + "%"};
		Integer results = jdbc.queryForObject(sb.toString(), parameters, Integer.class);
		
		
		logger.debug("getTotalComputers() successful");
		return results;
	}
}