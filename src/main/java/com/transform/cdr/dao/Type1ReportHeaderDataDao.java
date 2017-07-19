package com.transform.cdr.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.transform.cdr.model.Type1ReportHeaderData;

public interface Type1ReportHeaderDataDao extends CrudRepository<Type1ReportHeaderData, Integer> {

	public List<Type1ReportHeaderData> findByReportId(int reportId);

}
