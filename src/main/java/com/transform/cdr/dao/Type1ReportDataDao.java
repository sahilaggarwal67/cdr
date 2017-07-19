package com.transform.cdr.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.transform.cdr.model.Type1ReportData;

public interface Type1ReportDataDao extends CrudRepository<Type1ReportData, Integer> {
	public List<Type1ReportData> findByReportId(int reportId);

}
