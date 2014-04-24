package com.excilys.computerdatabase.mapper;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.service.CompanyService;

@Component
public class ComputerMapper
{
	@Autowired
	private CompanyService companyService;
	
	@Autowired
    private ResourceBundleMessageSource messageSource;
	
	public Computer fromDto(ComputerDto cDto)
	{
		Computer c = new Computer();
		if( cDto.getId() != null && !cDto.getId().isEmpty() )
		{
			c.setId(Integer.parseInt(cDto.getId()));
		}
		
		c.setName(cDto.getName());
		
		if( cDto.getCompanyId() != null && !cDto.getCompanyId().isEmpty() && !cDto.getCompanyId().equals("0") )
		{
			c.setCompany(companyService.getCompany(Integer.parseInt(cDto.getCompanyId())));
		}
		else
		{
			c.setCompany(null);
		}
		
		Locale locale = LocaleContextHolder.getLocale();
		String pattern = messageSource.getMessage("date.pattern.joda", null, locale);
		DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
		if( !cDto.getIntroduced().isEmpty())
		{
			DateTime dt = dtf.parseDateTime(cDto.getIntroduced());
			c.setIntroduced(dt);
		}
		else c.setIntroduced(null);
		
		if( !cDto.getDiscontinued().isEmpty() )
		{
			DateTime dt = dtf.parseDateTime(cDto.getDiscontinued());
			c.setDiscontinued(dt);
		}
		else c.setDiscontinued(null);
		
		return c;
	}
	
	public ComputerDto toDto(Computer c)
	{
		Locale locale = LocaleContextHolder.getLocale();
		String pattern = messageSource.getMessage("date.pattern.joda", null, locale);
		DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);

		return ComputerDto.builder()
		.id(c.getId() + "")
		.name(c.getName())
		.introduced((c.getIntroduced() != null)?dtf.print(c.getIntroduced()):null)
		.discontinued((c.getDiscontinued() != null)?dtf.print(c.getDiscontinued()):null)
		.companyId((c.getCompany() != null)?c.getCompany().getId() + "":null)
		.build();
	}
}