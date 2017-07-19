package com.transform.cdr.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.transform.cdr.model.Type1ReportManualEntries;

public interface Type1ReportManualEntriesDao extends CrudRepository<Type1ReportManualEntries, Integer> {
	public List<Type1ReportManualEntries> findByReportId(int reportId);

}
