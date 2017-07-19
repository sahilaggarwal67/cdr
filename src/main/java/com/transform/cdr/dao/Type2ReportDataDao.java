package com.transform.cdr.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.transform.cdr.model.Type2ReportData;

public interface Type2ReportDataDao extends CrudRepository<Type2ReportData, Integer> {

	public List<Type2ReportData> findByReportId(int reportId);

	@Query("SELECT t from Type2ReportData t where t.reportId IN (:reportIds) order by t.recordNo")
	public List<Type2ReportData> findByReportId(@Param("reportIds") List<Integer> reportIds);

}
