package com.transform.cdr.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transform.cdr.dao.CompanyDao;
import com.transform.cdr.exception.SuperException;
import com.transform.cdr.model.Company;
import com.transform.cdr.service.CompanyService;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	CompanyDao companyDao;

	@Transactional
	public void addCompany(Company company) {
		companyDao.save(company);
	}

	public List<Company> getCompaniesList() {
		return companyDao.getAllCompanies();
	}

	@Transactional
	public void updateCompany(Company company) {
		companyDao.save(company);
	}

	public Company getCompanyById(int id) {
		return companyDao.findOne(id);
	}

}
