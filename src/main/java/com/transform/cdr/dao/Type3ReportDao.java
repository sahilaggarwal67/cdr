package com.transform.cdr.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.transform.cdr.model.Type3Report;

public interface Type3ReportDao extends CrudRepository<Type3Report, Integer> {
	
	public List<Type3Report> findByType3ReportId(int type3ReportId);
}
