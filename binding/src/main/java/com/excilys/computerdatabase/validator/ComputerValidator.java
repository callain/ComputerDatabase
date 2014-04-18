package com.excilys.computerdatabase.validator;

import com.excilys.computerdatabase.dto.ComputerDto;

public class ComputerValidator implements Validator<ComputerDto>
{
	@Override
	public String validate(ComputerDto c)
	{
		String validation = "";
		String regexDate = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
		
		if( c.getName() == null || c.getName().length() < 2 ) validation += "1";
		else validation += "0";
		
		if(c.getIntroduced() != null && !c.getIntroduced().trim().isEmpty())
		{
			if( c.getIntroduced().matches(regexDate)) validation += "0";
			else validation += "1";
		}
		else{
			validation += "0";
		}
		
		if(c.getDiscontinued() != null && !c.getDiscontinued().trim().isEmpty())
		{
			if( c.getDiscontinued().matches(regexDate)) validation += "0";
			else validation += "1";
		}	
		else
		{
			validation += "0";
		}
		
		try
		{
			Integer.parseInt(c.getCompanyId());
			validation += "0";
		}
		catch(NumberFormatException e)
		{
			validation += "1";
		}
		
		return validation;
	}
}