package com.excilys.computerdatabase.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.excilys.computerdatabase.domain.Computer;

public interface ComputerDAO extends CrudRepository<Computer,Integer>
{
	Page<Computer> findByNameContainingOrCompanyNameContaining(String computerName, String companyName, Pageable pageable);
}
