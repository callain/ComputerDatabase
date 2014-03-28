package com.excilys.computerdatabase.service;

import java.sql.Connection;
import java.sql.SQLException;

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
		Connection connection = ConnectionFactory.INSTANCE.getConnection();
		try
		{
			connection.setAutoCommit(false);
			c = computerDAO.getComputer(id);
			connection.commit();
		}
		catch(SQLException e)
		{
			logger.error("getComputer(" + id + ") failed with: " + e.getMessage());
			try
			{
				logger.error("getComputer(" + id + ") Connection is being rollback()");
				connection.rollback();
			}
			catch (SQLException exception)
			{
				logger.error("getComputer(" + id + ") failed with: " + exception.getMessage());
			}
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
		
		Connection connection = ConnectionFactory.INSTANCE.getConnection();
		try
		{
			connection.setAutoCommit(false);
			
			computerId = computerDAO.addComputer(c);
			logDAO.addLog("Computer added with id: " + computerId);
			
			connection.commit();
		}
		catch(SQLException e)
		{
			logger.error("addComputer(" + c + ") failed with: " + e.getMessage());
			try
			{
				logger.error("addComputer(" + c + ") Connection is being rollback()");
				connection.rollback();
			}
			catch (SQLException exception)
			{
				logger.error("addComputer(" + c + ") failed with: "  + exception.getMessage());
			}
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
		Connection connection = ConnectionFactory.INSTANCE.getConnection();
		int computerUpdated = 0;
		try
		{
			connection.setAutoCommit(false);
		
			computerUpdated = computerDAO.updateComputer(c);
			logDAO.addLog("Computer update with id: " + c.getId());
			
			connection.commit();
		}
		catch(SQLException e)
		{
			logger.error("updateComputer(" + c + ") failed with: " + e.getMessage());
			try
			{
				logger.error("updateComputer(" + c + ") Connection is being rollback()");
				connection.rollback();
			}
			catch (SQLException exception)
			{
				logger.error("updateComputer(" + c + ") failed with: " + e.getMessage());
			}
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
		Connection connection = ConnectionFactory.INSTANCE.getConnection();
		
		try
		{
			connection.setAutoCommit(false);
			
			computerDAO.deleteComputer(id);
			logDAO.addLog("Computer deleted with id: " + id);
			
			connection.commit();
		}
		catch(SQLException e)
		{
			logger.error("deleteComputer(" + id + ") failed with: " + e.getMessage());
			try
			{
				logger.error("deleteComputer(" + id + ") Connection is being rollback()");
				connection.rollback();
			}
			catch(SQLException exception)
			{
				logger.error("deleteComputer(" + id + ") failed with: " + e.getMessage());	
			}
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
		Connection connection = ConnectionFactory.INSTANCE.getConnection();
		ComputerWrapper computerWrapper = null; 
		try
		{
			connection.setAutoCommit(false);
			
			computerWrapper = new ComputerWrapper(computerDAO.getComputers(qb));

			connection.commit();
		}
		catch (SQLException e)
		{
			logger.error("getComputers() failed with: " + e.getMessage());
			try{
				logger.error("getComputers() Connection is being rollback()");
				connection.rollback();
			}
			catch(SQLException exception) {
				logger.error("getComputers() failed with: " + exception.getMessage());
			}
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
		Connection connection = ConnectionFactory.INSTANCE.getConnection();
		int results = 0;
		try
		{
			connection.setAutoCommit(false);
			
			results = computerDAO.getTotalComputers(qb);
			
			connection.commit();
		}
		catch (SQLException e)
		{
			logger.error("getTotalComputers() failed with: " + e.getMessage());
			try
			{
				logger.error("getTotalComputers() Connection is being rollback()");
				connection.rollback();
			}
			catch (SQLException exception)
			{
				logger.error("getTotalComputers() failed with: " + e.getMessage());
			}
		}
		finally
		{
			ConnectionFactory.INSTANCE.closeConnection();
		}
		
		return results;
	}
}