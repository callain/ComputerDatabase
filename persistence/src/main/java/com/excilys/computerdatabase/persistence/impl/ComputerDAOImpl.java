package com.excilys.computerdatabase.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.exception.SQLQueryFailException;
import com.excilys.computerdatabase.persistence.ComputerDAO;
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
		
		List<Computer> computerList = new ArrayList<Computer>();
		
		String search = (qb.getSearch() == null)? "%%" : "%" + qb.getSearch() + "%";
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Computer.class, "computer")
				.createAlias("company", "company", JoinType.LEFT_OUTER_JOIN )
				.add(Restrictions.or(Restrictions.like("computer.name", search),Restrictions.like("company.name", search)))
				.setFirstResult(qb.getOffset())
				.setMaxResults(qb.getNbRows());
		
		if( qb.getDirection() ) criteria = criteria.addOrder(Order.desc(qb.getField()));
		else criteria = criteria.addOrder(Order.asc(qb.getField()));

		computerList = criteria.list();

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
		
		String search = (qb.getSearch() == null)? "%%" : "%" + qb.getSearch() + "%";
		int result = ((Number) sessionFactory.getCurrentSession().createCriteria(Computer.class, "computer")
				.createAlias("company", "company", JoinType.LEFT_OUTER_JOIN )
				.add(Restrictions.or(Restrictions.like("computer.name", search),Restrictions.like("company.name", search)))
				.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		
		logger.debug("getTotalComputers() successful");
		return result;
	}
}