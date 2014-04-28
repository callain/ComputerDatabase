package com.excilys.computerdatabase.persistence;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.excilys.computerdatabase.domain.Company;

public interface CompanyDAO extends CrudRepository<Company,Integer>
{
	List<Company> findAll(Sort sort);
}