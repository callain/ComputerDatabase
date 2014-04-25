package com.excilys.computerdatabase.persistence.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.QCompany;
import com.excilys.computerdatabase.domain.QComputer;
import com.excilys.computerdatabase.exception.SQLQueryFailException;
import com.excilys.computerdatabase.persistence.ComputerDAO;
import com.excilys.computerdatabase.persistence.QueryBuilder;
import com.mysema.query.jpa.hibernate.HibernateQuery;

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
		
		HibernateQuery query = new HibernateQuery(sessionFactory.getCurrentSession());
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		
		query.from(computer).leftJoin(computer.company, company);
		
		if( qb.getSearch() != null )
		{
			String search = "%" + qb.getSearch() + "%"; 
			query.where(computer.name.like(search).or(company.name.like(search)));
		}
			
		query.offset(qb.getOffset()).limit(qb.getNbRows());
		
		switch(qb.getField())
		{
			case "computer.name" :
				if( qb.getDirection() ) query.orderBy(computer.name.desc());
				else query.orderBy(computer.name.asc());
				
				break;
			
			case "computer.introduced" :
				if( qb.getDirection() ) query.orderBy(computer.introduced.desc());
				else query.orderBy(computer.introduced.asc());
				
				break;
			
			case "computer.discontinued" :
				if( qb.getDirection() ) query.orderBy(computer.discontinued.desc());
				else query.orderBy(computer.discontinued.asc());
				
				break;
				
			case "company.name" :
				if( qb.getDirection() ) query.orderBy(computer.company.name.desc());
				else query.orderBy(computer.company.name.asc());
				
				break;
		}
		
		List<Computer> computerList = query.list(computer);

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

		HibernateQuery query = new HibernateQuery(sessionFactory.getCurrentSession());
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		
		query.from(computer).leftJoin(computer.company, company);
		
		if( qb.getSearch() != null )
		{
			String search = "%" + qb.getSearch() + "%"; 
			query.where(computer.name.like(search).or(company.name.like(search)));
		}

		Long result = query.count();

		logger.debug("getTotalComputers() successful");
		return result.intValue();
	}
}