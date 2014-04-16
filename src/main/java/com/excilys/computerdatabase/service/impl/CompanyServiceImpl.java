package com.excilys.computerdatabase.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.dao.impl.CompanyDAOImpl;
import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.wrapper.CompanyWrapper;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

	private final static Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);

	@Autowired
	private CompanyDAOImpl companyDAO;
	
	@Override
	@Transactional(readOnly = true)
	public Company getCompany(int id)
	{
		logger.debug("getCompany(" + id + ")");
		Company c = null;
		c = companyDAO.getCompany(id);
		
		return c;
	}
	
	@Override
	@Transactional(readOnly = true)
	public CompanyWrapper getCompanies()
	{
		logger.debug("getCompanies()");
		CompanyWrapper computerWrapper = null;
		computerWrapper = new CompanyWrapper(companyDAO.getCompanies());
		
		return computerWrapper;
	}
}
