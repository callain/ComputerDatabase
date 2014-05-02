package com.excilys.computerdatabase.web.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.computerdatabase.persistence.QueryBuilder;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.web.service.WSComputerDatabaseService;
import com.excilys.computerdatabase.wrapper.ComputerWrapper;


/**
 * http://localhost:8080/service-jax-rs/service/computers/search/${search}
 */
public class WSComputerDatabaseServiceImpl implements WSComputerDatabaseService
{
	private static final Logger logger = LoggerFactory.getLogger(WSComputerDatabaseServiceImpl.class);

	@Autowired
	private ComputerService computerService;

	public ComputerWrapper getComputers(String search)
	{
		logger.debug("WSComputerServiceImpl.getComputers(" + search + ")");
		QueryBuilder qb = new QueryBuilder();
		qb.setSearch(search);
		ComputerWrapper computerWrapper = computerService.getComputers(qb);

		return computerWrapper;
	}

}