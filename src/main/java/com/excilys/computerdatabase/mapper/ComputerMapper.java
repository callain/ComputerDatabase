package com.excilys.computerdatabase.mapper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.service.CompanyService;

@Component
public class ComputerMapper {
	
	@Autowired
	private CompanyService companyService;
	
	public Computer fromDto(ComputerDto cDto) {
		Computer c = new Computer();
		if( cDto.getId() != null && !cDto.getId().isEmpty() ) c.setId(Integer.parseInt(cDto.getId()));
		c.setName(cDto.getName());
		c.setCompany(companyService.getCompany(Integer.parseInt(cDto.getCompanyId())));
		c.setIntroduced(Timestamp.valueOf(cDto.getIntroduced() + " 00:00:00"));
		c.setDiscontinued(Timestamp.valueOf(cDto.getDiscontinued() + " 00:00:00"));
		
		return c;
	}
	
	public ComputerDto toDto(Computer c) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return ComputerDto.builder()
		.id(c.getId() + "")
		.name(c.getName())
		.introduced(formatter.format(new Date(c.getIntroduced().getTime())))
		.discontinued(formatter.format(new Date(c.getDiscontinued().getTime())))
		.companyId(c.getCompany().getId() + "")
		.build();
	}
}