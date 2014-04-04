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
public class ComputerMapper
{
	@Autowired
	private CompanyService companyService;
	
	public Computer fromDto(ComputerDto cDto)
	{
		Computer c = new Computer();
		if( cDto.getId() != null && !cDto.getId().isEmpty() ) c.setId(Integer.parseInt(cDto.getId()));
		c.setName(cDto.getName());
		if( cDto.getCompanyId() != null && !cDto.getCompanyId().isEmpty() && !cDto.getCompanyId().equals("0") )
			c.setCompany(companyService.getCompany(Integer.parseInt(cDto.getCompanyId())));
		else c.setCompany(null);
		try
		{
			Timestamp introduced = Timestamp.valueOf(cDto.getIntroduced() + " 00:00:00");
			c.setIntroduced(introduced);
		}
		catch(IllegalArgumentException e)
		{
			c.setIntroduced(null);
		}
		try
		{
			Timestamp discontinued = Timestamp.valueOf(cDto.getDiscontinued() + " 00:00:00");
			c.setDiscontinued(discontinued);
		}
		catch(IllegalArgumentException e)
		{
			c.setDiscontinued(null);
		}
		
		return c;
	}
	
	public ComputerDto toDto(Computer c)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return ComputerDto.builder()
		.id(c.getId() + "")
		.name(c.getName())
		.introduced((c.getIntroduced() != null)?formatter.format(new Date(c.getIntroduced().getTime())):null)
		.discontinued((c.getDiscontinued() != null)?formatter.format(new Date(c.getDiscontinued().getTime())):null)
		.companyId(c.getCompany().getId() + "")
		.build();
	}
}