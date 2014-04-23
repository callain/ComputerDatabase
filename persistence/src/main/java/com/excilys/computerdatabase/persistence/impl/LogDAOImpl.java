package com.excilys.computerdatabase.persistence.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.exception.SQLQueryFailException;
import com.excilys.computerdatabase.persistence.LogDAO;

@Repository
public class LogDAOImpl implements LogDAO
{
	final Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public boolean addLog(String log) throws SQLQueryFailException
	{
		logger.debug("addLog(" + log + ")");

		Query query = sessionFactory.getCurrentSession().createQuery("INSERT INTO Log(query) values(:log)").setString("log", log);
		int results = query.executeUpdate();
		
		logger.debug("addLog(" + log + ") successful");
		return results >= 1;
	}
}