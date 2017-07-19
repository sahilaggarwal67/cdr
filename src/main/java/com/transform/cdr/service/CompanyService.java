package com.transform.cdr.service;

import java.util.List;

import com.transform.cdr.exception.SuperException;
import com.transform.cdr.model.Company;

public interface CompanyService {

	public void addCompany(Company company);

	public List<Company> getCompaniesList();

	public void updateCompany(Company company);

	public Company getCompanyById(int id);

}
