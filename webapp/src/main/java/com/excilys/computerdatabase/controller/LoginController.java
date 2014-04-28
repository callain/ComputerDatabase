package com.excilys.computerdatabase.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/login")
public class LoginController
{
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet()
	{
		logger.debug("LoginController.doGet()");
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		boolean checkAuthority = false;
		for(GrantedAuthority ga : authorities)
		{
			if( ga.getAuthority().equals("ROLE_USER") || ga.getAuthority().equals("ROLE_ADMIN") )
			{
				checkAuthority = true;
			}
		}
		
		if( checkAuthority )
		{
			return "redirect:computers";
		}
		
		return "login";
	}
}