package com.excilys.computerdatabase.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.exception.SQLQueryFailException;
import com.excilys.computerdatabase.persistence.ComputerDAO;
import com.excilys.computerdatabase.persistence.ComputerField;
import com.excilys.computerdatabase.persistence.QueryBuilder;

@Repository
public class ComputerDAOImpl implements ComputerDAO
{
	private final Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Computer getComputer(int id) throws SQLQueryFailException
	{
		logger.debug("getComputer(" + id + ")");

		Computer computer = (Computer) sessionFactory.getCurrentSession().get(Computer.class, id);
		
		logger.debug("getComputer(" + id + ") successful");
		return computer;
	}
	
	@Override
	public List<Computer> getComputers(QueryBuilder qb) throws SQLQueryFailException
	{
		logger.debug("getComputers(" + qb + ")");
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT computer ");
		sb.append("FROM Computer computer ");
		sb.append("LEFT JOIN computer.company company ");
		sb.append("WHERE computer.name LIKE :search OR company.name LIKE :search ");
		
		if( qb.getDirection() ) sb.append(String.format("ORDER BY %s DESC ", qb.getField()));
		else sb.append(String.format("ORDER BY %s ", qb.getField()));
		
		List<Computer> computerList = new ArrayList<Computer>();
		
		String search = (qb.getSearch() == null)? "%%" : "%" + qb.getSearch() + "%";
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString()).setString("search", search).setFirstResult(qb.getOffset()).setMaxResults(qb.getNbRows());
		computerList = query.list();

		logger.debug("getComputers() successful");
		return computerList;
	}

	@Override
	public int addComputer(Computer c) throws SQLQueryFailException
	{
		logger.debug("addComputer(" + c + ")");

		sessionFactory.getCurrentSession().persist(c);

		logger.debug("addComputer(" + c + ") successful");
		return c.getId();
	}
	
	@Override
	public void updateComputer(Computer c) throws SQLQueryFailException
	{
		logger.debug("updateComputer(" + c + ")");
		
		sessionFactory.getCurrentSession().merge(c);
		
		logger.debug("updateComputer(" + c + ") successful");	
	}
	
	@Override
	public boolean deleteComputer(int id) throws SQLQueryFailException
	{
		logger.debug("deleteComputer(" + id + ")");
		
		Query query = sessionFactory.getCurrentSession().createQuery("delete from Computer where id = :id").setInteger("id", id);
		int rowsAffected = query.executeUpdate();
		
		logger.debug("deleteComputer(" + id + ") successful");
		return rowsAffected >= 1;
	}

	@Override
	public int getTotalComputers(QueryBuilder qb) throws SQLQueryFailException
	{
		logger.debug("getTotalComputers(" + qb + ")");
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT count(*) ");
		sb.append("FROM Computer computer ");
		sb.append("LEFT JOIN computer.company company ");
		sb.append("WHERE computer.name LIKE :search OR company.name LIKE :search ");
		
		String field = (qb.getField() == null)? ComputerField.NAME.getName() : qb.getField();
		if( qb.getDirection() )
		{
			sb.append(String.format("ORDER BY %s DESC ", field));
		}
		else
		{
			sb.append(String.format("ORDER BY %s ", field));
		}

		String search = (qb.getSearch() == null)? "%%" : "%" + qb.getSearch() + "%";
		
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString()).setString("search", search);
		int result = ((Long) query.list().get(0)).intValue() ;

		logger.debug("getTotalComputers() successful");
		return result;
	}
}