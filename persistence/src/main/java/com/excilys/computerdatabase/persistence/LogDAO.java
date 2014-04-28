package com.excilys.computerdatabase.persistence;

import org.springframework.data.repository.CrudRepository;

import com.excilys.computerdatabase.domain.Log;

public interface LogDAO extends CrudRepository<Log,Integer>
{
	
}