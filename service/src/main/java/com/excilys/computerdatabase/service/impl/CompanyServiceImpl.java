package com.excilys.computerdatabase.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.persistence.CompanyDAO;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.wrapper.CompanyWrapper;
import com.google.common.collect.Lists;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService
{
	private final static Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

	@Autowired
	private CompanyDAO companyDAO;
	
	@Override
	@Transactional(readOnly = true)
	public Company getCompany(int id)
	{
		logger.debug("getCompany(" + id + ")");
		
		Company c = null;
		c = companyDAO.findOne(id);
		
		return c;
	}
	
	@Override
	@Transactional(readOnly = true)
	public CompanyWrapper getCompanies()
	{
		logger.debug("getCompanies()");
		
		CompanyWrapper companyWrapper = null;
		companyWrapper = new CompanyWrapper(Lists.newArrayList(companyDAO.findAll(new Sort(Sort.Direction.ASC, "name"))));
		
		return companyWrapper;
	}
}