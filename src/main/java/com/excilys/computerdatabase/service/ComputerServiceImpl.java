package com.excilys.computerdatabase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.dao.ComputerDAOImpl;
import com.excilys.computerdatabase.dao.ConnectionFactory;
import com.excilys.computerdatabase.dao.LogDAO;
import com.excilys.computerdatabase.dao.LogDAOImpl;
import com.excilys.computerdatabase.dao.QueryBuilder;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.ComputerWrapper;
import com.excilys.computerdatabase.exception.SQLQueryFailException;

public enum ComputerServiceImpl implements ComputerService{

	INSTANCE;
	
	private static final Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);
	private ComputerDAO computerDAO;
	private LogDAO logDAO;
	
	{
		computerDAO = ComputerDAOImpl.INSTANCE;
		logDAO = LogDAOImpl.INSTANCE;
	}
	
	@Override
	public Computer getComputer(int id)
	{
		Computer c = null;
		try
		{
			c = computerDAO.getComputer(id);
		}
		catch(SQLQueryFailException e)
		{
			logger.error("getComputer(" + id + ") failed with: " + e.getMessage());
		}
		finally
		{
			ConnectionFactory.INSTANCE.closeConnection();
		}
		
		return c;
	}

	@Override
	public int addComputer(Computer c)
	{
		int computerId = 0;
		
		try
		{
			ConnectionFactory.INSTANCE.startTransaction();
			
			computerId = computerDAO.addComputer(c);
			logDAO.addLog("Computer added with id: " + computerId);
			
			ConnectionFactory.INSTANCE.commitTransaction();
		}
		catch(SQLQueryFailException e)
		{
			logger.error("addComputer(" + c + ") failed with: " + e.getMessage());
			ConnectionFactory.INSTANCE.rollback();
			throw e;
		}
		finally
		{
			ConnectionFactory.INSTANCE.closeConnection();
		}
		
		return computerId;
	}

	@Override
	public int updateComputer(Computer c)
	{
		int computerUpdated = 0;
		try
		{
			ConnectionFactory.INSTANCE.startTransaction();
		
			computerUpdated = computerDAO.updateComputer(c);
			logDAO.addLog("Computer update with id: " + c.getId());
			
			ConnectionFactory.INSTANCE.commitTransaction();
		}
		catch(SQLQueryFailException e)
		{
			logger.error("updateComputer(" + c + ") failed with: " + e.getMessage());
			ConnectionFactory.INSTANCE.rollback();
		}
		finally
		{
			ConnectionFactory.INSTANCE.closeConnection();
		}
		
		return computerUpdated;
	}

	@Override
	public boolean deleteComputer(int id)
	{
		boolean computerDeleted = false;
		try
		{
			ConnectionFactory.INSTANCE.startTransaction();
			
			computerDAO.deleteComputer(id);
			logDAO.addLog("Computer deleted with id: " + id);
			
			ConnectionFactory.INSTANCE.commitTransaction();
		}
		catch(SQLQueryFailException e)
		{
			logger.error("deleteComputer(" + id + ") failed with: " + e.getMessage());
			ConnectionFactory.INSTANCE.rollback();
		}
		finally
		{
			ConnectionFactory.INSTANCE.closeConnection();
		}
		
		return computerDeleted;
	}
	
	@Override
	public ComputerWrapper getComputers(QueryBuilder qb)
	{
		ComputerWrapper computerWrapper = null; 
		try
		{
			computerWrapper = new ComputerWrapper(computerDAO.getComputers(qb));
		}
		catch (SQLQueryFailException e)
		{
			logger.error("getComputers() failed with: " + e.getMessage());
		} 
		finally
		{
			ConnectionFactory.INSTANCE.closeConnection();
		}
		
		return computerWrapper;
	}

	@Override
	public int getTotalComputers(QueryBuilder qb)
	{
		int results = 0;
		try
		{
			results = computerDAO.getTotalComputers(qb);
		}
		catch (SQLQueryFailException e)
		{
			logger.error("getTotalComputers() failed with: " + e.getMessage());
		}
		finally
		{
			ConnectionFactory.INSTANCE.closeConnection();
		}
		
		return results;
	}
}