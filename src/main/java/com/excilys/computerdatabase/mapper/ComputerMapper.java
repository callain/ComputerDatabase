package com.excilys.computerdatabase.mapper;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.service.CompanyService;

public class ComputerMapper {
	
	@Autowired
	private CompanyService companyService;
	
	public Computer fromDto(ComputerDto cDto) {
		Computer c = new Computer();
		c.setId(Integer.parseInt(cDto.getId()));
		c.setName(cDto.getName());
		c.setCompany(companyService.getCompany(Integer.parseInt(cDto.getId())));
		c.setIntroduced(Timestamp.valueOf(cDto.getIntroduced() + " 00:00:00"));
		c.setDiscontinued(Timestamp.valueOf(cDto.getDiscontinued() + " 00:00:00"));
		
		return c;
	}
}
