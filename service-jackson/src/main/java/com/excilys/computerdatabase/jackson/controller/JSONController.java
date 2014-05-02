package com.excilys.computerdatabase.jackson.controller;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computerdatabase.persistence.QueryBuilder;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.wrapper.ComputerWrapper;

@RestController
@RequestMapping("/computer")
public class JSONController
{
	@Autowired
	private ComputerService computerService;
	
	@RequestMapping(value="{search}", method = RequestMethod.GET)
	@Produces(value = MediaType.APPLICATION_JSON)
	public @ResponseBody ComputerWrapper getShopInJSON(@PathVariable String search)
	{
		QueryBuilder qb = new QueryBuilder();
		qb.setSearch(search);
		ComputerWrapper computerWrapper = computerService.getComputers(qb);

		return computerWrapper;
	}
}