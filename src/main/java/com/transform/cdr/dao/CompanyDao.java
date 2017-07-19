package com.transform.cdr.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.transform.cdr.model.Company;

public interface CompanyDao extends CrudRepository<Company, Integer> {

	@Query("Select c from Company c")
	public List<Company> getAllCompanies();

}
