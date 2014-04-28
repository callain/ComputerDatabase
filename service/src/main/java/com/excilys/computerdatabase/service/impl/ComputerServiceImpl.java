package com.excilys.computerdatabase.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.domain.Log;
import com.excilys.computerdatabase.persistence.ComputerDAO;
import com.excilys.computerdatabase.persistence.ComputerField;
import com.excilys.computerdatabase.persistence.LogDAO;
import com.excilys.computerdatabase.persistence.QueryBuilder;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.wrapper.ComputerWrapper;

@Service("computerService")
public class ComputerServiceImpl implements ComputerService
{
	private static final Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);
	
	@Autowired
	private ComputerDAO computerDAO;
	
	@Autowired
	private LogDAO logDAO;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public Computer getComputer(int id)
	{
		logger.debug("ComputerServiceImpl.getComputer(" + id + ")");
		
		Computer c = null;
		c = computerDAO.findOne(id);
		
		return c;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public int addComputer(Computer c)
	{
		logger.debug("ComputerServiceImpl.addComputer(" + c + ")");
		int computerId = 0;
		
		computerDAO.save(c);
		logDAO.save(new Log("Computer added with id: " + computerId));

		return c.getId();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void updateComputer(Computer c)
	{
		logger.debug("ComputerServiceImpl.updateComputer(" + c + ")");
		
		computerDAO.save(c);
		
		logDAO.save(new Log("Computer updated with id: " + c.getId()));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteComputer(int id)
	{
		logger.debug("ComputerServiceImpl.deleteComputer(" + id + ")");
		
		computerDAO.delete(id);

		logDAO.save(new Log("Computer deleted with id: " + id));
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public ComputerWrapper getComputers(QueryBuilder qb)
	{
		logger.debug("ComputerServiceImpl.getComputers(" + qb + ")");
		
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
		PageRequest pageRequest = new PageRequest(qb.getCurrentPage() - 1, qb.getNbRows(), (qb.getDirection())?Direction.DESC:Direction.ASC, qb.getField());
		Page<Computer> pageComputers = computerDAO.findByNameContainingOrCompanyName(qb.getSearch(), qb.getSearch(), pageRequest);

		int results = (int) pageComputers.getTotalElements();
		
		computerWrapper = new ComputerWrapper(pageComputers.getContent());
		computerWrapper.setSearch(qb.getSearch());
		computerWrapper.setPages((int) Math.ceil((double) results / (double) qb.getNbRows()));
		computerWrapper.setResults(results);
		computerWrapper.setCurrentPage(qb.getCurrentPage());
		computerWrapper.setField(cf);
		computerWrapper.setDesc(qb.getDirection());
		
		return computerWrapper;
	}
}