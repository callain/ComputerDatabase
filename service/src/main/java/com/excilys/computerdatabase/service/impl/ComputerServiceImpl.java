package com.excilys.computerdatabase.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.persistence.ComputerField;
import com.excilys.computerdatabase.persistence.ConnectionFactory;
import com.excilys.computerdatabase.persistence.QueryBuilder;
import com.excilys.computerdatabase.persistence.impl.ComputerDAOImpl;
import com.excilys.computerdatabase.persistence.impl.LogDAOImpl;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.wrapper.ComputerWrapper;

@Service("computerService")
public class ComputerServiceImpl implements ComputerService
{
	private static final Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);
	
	@Autowired
	private ComputerDAOImpl computerDAO;
	
	@Autowired
	private LogDAOImpl logDAO;
	
	@Autowired
	private ConnectionFactory connectionFactory;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public Computer getComputer(int id)
	{
		logger.debug("ComputerServiceImpl.getComputer(" + id + ")");
		Computer c = null;
		c = computerDAO.getComputer(id);
		
		return c;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int addComputer(Computer c)
	{
		int computerId = 0;
		
		computerId = computerDAO.addComputer(c);
		logDAO.addLog("Computer added with id: " + computerId);

		return computerId;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int updateComputer(Computer c)
	{
		int computerUpdated = 0;
		computerUpdated = computerDAO.updateComputer(c);
		logDAO.addLog("Computer updated with id: " + c.getId());
		
		return computerUpdated;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean deleteComputer(int id)
	{
		boolean computerDeleted = false;
		
		computerDAO.deleteComputer(id);
		logDAO.addLog("Computer deleted with id: " + id);
		
		return computerDeleted;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public ComputerWrapper getComputers(QueryBuilder qb)
	{
		ComputerWrapper computerWrapper = null; 
		String field;
		ComputerField cf;
		
		try
		{
			if( qb.getField() != null )
			{
				cf = ComputerField.valueOf(qb.getField());
				field = cf.getName();
			}
			else
			{
				cf = ComputerField.NAME;
				field = cf.getName();
			}
		}
		catch(IllegalArgumentException e)
		{
			cf = ComputerField.NAME;
			field = cf.getName();
		}
		
		qb.setField(field);
		computerWrapper = new ComputerWrapper(computerDAO.getComputers(qb));
		
		int results = computerDAO.getTotalComputers(qb);
		computerWrapper.setPages((int) Math.ceil((double) results / (double) qb.getNbRows()));
		computerWrapper.setResults(results);
		computerWrapper.setCurrentPage(qb.getCurrentPage());
		computerWrapper.setField(cf);
		computerWrapper.setDesc(qb.getDirection());
		
		return computerWrapper;
	}

	@Override
	@Transactional(readOnly = true)
	public int getTotalComputers(QueryBuilder qb)
	{
		int results = 0;
		results = computerDAO.getTotalComputers(qb);
		
		return results;
	}
}