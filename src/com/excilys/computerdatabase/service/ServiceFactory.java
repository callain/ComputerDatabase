package com.excilys.computerdatabase.service;

/**
 * Service singleton
 */
public class ServiceFactory {

	private static ComputerService computerService;
	private static CompanyService companyService;
	
	private ServiceFactory() {
		
	}

	public static ComputerService getComputerService() {
		if( computerService == null ) {
			computerService = new ComputerServiceImpl();
		}
		return computerService;
	}

	public static CompanyService getCompanyService() {
		if( companyService == null ) {
			companyService = new CompanyServiceImpl();
		}
		return companyService;
	}
}
