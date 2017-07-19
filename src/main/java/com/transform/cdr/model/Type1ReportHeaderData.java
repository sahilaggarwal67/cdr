package com.transform.cdr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "type1reportheaderdata")
public class Type1ReportHeaderData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column(name = "report_id")
	private int reportId;
	@Column(name = "header_name")
	private String headerName;
	@Column(name = "actual_header_name")
	private String actualHeaderName;

	public Type1ReportHeaderData() {
		super();
	}

	public Type1ReportHeaderData(int id, int reportId, String headerName, String actualHeaderName) {
		super();
		this.id = id;
		this.reportId = reportId;
		this.headerName = headerName;
		this.actualHeaderName = actualHeaderName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public String getActualHeaderName() {
		return actualHeaderName;
	}

	public void setActualHeaderName(String actualHeaderName) {
		this.actualHeaderName = actualHeaderName;
	}

}
