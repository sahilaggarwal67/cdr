package com.transform.cdr.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.transform.cdr.model.Type1Report;

public interface Type1ReportDao extends CrudRepository<Type1Report, Integer> {

	public List<Type1Report> findByType2ReportId(int type2ReportId);

	@Query("Select min(t1.year) from Type1Report t1")
	public Integer findMinYear();

	@Query("Select max(t1.year) from Type1Report t1")
	public Integer findMaxYear();
	
	public List<Type1Report> findByYearAndMonth(String year,String month);
}
