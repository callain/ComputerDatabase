package com.excilys.computerdatabase.validator;

import java.util.Locale;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.excilys.computerdatabase.annotation.Date;

public class DateValidator implements ConstraintValidator<Date, String>
{
	@Autowired
    private ResourceBundleMessageSource messageSource;
	
	@Override
	public void initialize(Date date)
	{
	
	}

	@Override
	public boolean isValid(String field, ConstraintValidatorContext arg1)
	{
		if( field.isEmpty() ) return true;
		
		Locale locale = LocaleContextHolder.getLocale();
		String pattern = messageSource.getMessage("date.pattern.joda", null, locale);
		DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
		boolean isValid = true;
		try
		{
			DateTime dt = dtf.parseDateTime(field);
			if( dt.getYear() < 1970 || dt.getYear() > 2038 ) isValid = false;
		}
		catch (IllegalArgumentException e)
		{
			isValid = false;
		}
		
		return isValid;
	}
}
