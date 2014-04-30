package com.excilys.computerdatabase.web.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.persistence.QueryBuilder;
import com.excilys.computerdatabase.wrapper.CompanyWrapper;
import com.excilys.computerdatabase.wrapper.ComputerWrapper;

@WebService
@SOAPBinding(style = Style.RPC)
public interface WSComputerDatabaseService
{
	@WebMethod public Computer getComputer(@WebParam(name="id") int id);
	@WebMethod public ComputerWrapper getComputers(@WebParam(name="qb") QueryBuilder qb);
	@WebMethod public int addComputer(@WebParam(name="c") Computer c);
	@WebMethod public void updateComputer(@WebParam(name="c") Computer c);
	@WebMethod public void deleteComputer(@WebParam(name="id") int id);
	
	@WebMethod public Company getCompany(@WebParam(name="id") int id);
	@WebMethod public CompanyWrapper getCompanies();
}