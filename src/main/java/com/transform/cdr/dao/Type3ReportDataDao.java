package com.transform.cdr.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.transform.cdr.model.Type3ReportData;

public interface Type3ReportDataDao extends CrudRepository<Type3ReportData, Integer> {
	public List<Type3ReportData> findByReportId(int reportId);

}
