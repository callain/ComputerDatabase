package com.excilys.computerdatabase.persistence.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.QCompany;
import com.excilys.computerdatabase.exception.SQLQueryFailException;
import com.excilys.computerdatabase.persistence.CompanyDAO;
import com.mysema.query.jpa.hibernate.HibernateQuery;

@Repository
public class CompanyDAOImpl implements CompanyDAO
{
	private final static Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Company getCompany(int id) throws SQLQueryFailException
	{
		logger.debug("getCompany(" + id + ")");

		Company company = (Company) sessionFactory.getCurrentSession().get(Company.class, id);

		logger.debug("getCompany(" + id + ") successful");
		return company;
	}

	@Override
	public List<Company> getCompanies()  throws SQLQueryFailException
	{
		logger.debug("getCompanies()");

		HibernateQuery query = new HibernateQuery(sessionFactory.getCurrentSession());
		QCompany company = QCompany.company;
		
		List<Company> companies = query.from(company).orderBy(company.name.asc()).list(company);
		
		logger.debug("getCompanies() successful");
		return companies;
	}
}