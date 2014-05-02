package com.excilys.computerdatabase.web.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.wrapper.ComputerWrapper;

@Produces("application/xml")
@Service("WSComputerDatabaseService")
@Path("/computers")
public interface WSComputerDatabaseService
{
	@GET
	@Path("/search/{search}")
	public ComputerWrapper getComputers(@PathParam("search") String search);
}