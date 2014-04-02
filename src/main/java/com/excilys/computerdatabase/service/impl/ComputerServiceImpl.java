package com.excilys.computerdatabase.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.dao.ConnectionFactory;
import com.excilys.computerdatabase.dao.QueryBuilder;
import com.excilys.computerdatabase.dao.impl.ComputerDAOImpl;
import com.excilys.computerdatabase.dao.impl.LogDAOImpl;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.ComputerWrapper;
import com.excilys.computerdatabase.exception.SQLQueryFailException;
import com.excilys.computerdatabase.service.ComputerService;

@Service("computerService")
public class ComputerServiceImpl implements ComputerService{

	private static final Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);
	
	@Autowired
	private ComputerDAOImpl computerDAO;
	
	@Autowired
	private LogDAOImpl logDAO;
	
	@Autowired
	private ConnectionFactory connectionFactory;

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
			connectionFactory.closeConnection();
		}
		
		return c;
	}

	@Override
	public int addComputer(Computer c)
	{
		int computerId = 0;
		
		try
		{
			connectionFactory.startTransaction();
			
			computerId = computerDAO.addComputer(c);
			logDAO.addLog("Computer added with id: " + computerId);
			
			connectionFactory.commitTransaction();
		}
		catch(SQLQueryFailException e)
		{
			logger.error("addComputer(" + c + ") failed with: " + e.getMessage());
			connectionFactory.rollback();
			throw e;
		}
		finally
		{
			connectionFactory.closeConnection();
		}
		
		return computerId;
	}

	@Override
	public int updateComputer(Computer c)
	{
		int computerUpdated = 0;
		try
		{
			connectionFactory.startTransaction();
		
			computerUpdated = computerDAO.updateComputer(c);
			logDAO.addLog("Computer update with id: " + c.getId());
			
			connectionFactory.commitTransaction();
		}
		catch(SQLQueryFailException e)
		{
			logger.error("updateComputer(" + c + ") failed with: " + e.getMessage());
			connectionFactory.rollback();
		}
		finally
		{
			connectionFactory.closeConnection();
		}
		
		return computerUpdated;
	}

	@Override
	public boolean deleteComputer(int id)
	{
		boolean computerDeleted = false;
		try
		{
			connectionFactory.startTransaction();
			
			computerDAO.deleteComputer(id);
			logDAO.addLog("Computer deleted with id: " + id);
			
			connectionFactory.commitTransaction();
		}
		catch(SQLQueryFailException e)
		{
			logger.error("deleteComputer(" + id + ") failed with: " + e.getMessage());
			connectionFactory.rollback();
		}
		finally
		{
			connectionFactory.closeConnection();
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
			connectionFactory.closeConnection();
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
			connectionFactory.closeConnection();
		}
		
		return results;
	}
}