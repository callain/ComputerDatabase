package com.excilys.computerdatabase.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.excilys.computerdatabase.dto.ComputerDto;

public class ComputerValidator implements Validator<ComputerDto>
{
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public int validate(ComputerDto c)
	{
		int validation = 0;
		
		if( c.getName() == null || c.getName().length() < 2 )
		{
			validation += 1;
		}
		
		try
		{
			Integer.parseInt(c.getCompanyId());
		}
		catch(NumberFormatException e)
		{
			validation += 2;
		}
		
		if(c.getIntroduced() != null && !c.getIntroduced().trim().isEmpty())
		{
			try
			{
				DATE_FORMAT.parse(c.getIntroduced());
			}
			catch (ParseException e)
			{
				validation += 4;
	        }
		}
		
		if(c.getDiscontinued() != null && !c.getDiscontinued().trim().isEmpty())
		{
			try
			{
				DATE_FORMAT.parse(c.getDiscontinued());
			}
			catch (ParseException e)
			{
				validation += 8;
	        }
		}	
		
		return validation;
	}
}