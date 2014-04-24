package com.excilys.computerdatabase.persistence.impl;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.exception.SQLQueryFailException;
import com.excilys.computerdatabase.persistence.LogDAO;

@Repository
public class LogDAOImpl implements LogDAO
{
	final Logger logger = LoggerFactory.getLogger(ComputerDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addLog(Log log) throws SQLQueryFailException
	{
		logger.debug("addLog(" + log + ")");

		sessionFactory.getCurrentSession().persist(log);
		
		logger.debug("addLog(" + log + ") successful");
	}
}