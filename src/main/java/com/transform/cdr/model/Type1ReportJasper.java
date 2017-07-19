package com.transform.cdr.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

public class Type1ReportJasper implements Serializable {
	private String monthName;
	private String monthlyFee;
	private String staticIpFee;
	private String createdBy;
	private String reportName;
	private String year;
	private String businessLineCalls;
	private String rebate;
	private String rebate1;
	private String rebate2;
	private String overallTotal;
	private String totalData;
	private Map<Integer, String> manualEntry;
	private Map<Integer, String> manualEntryName;

	public Type1ReportJasper() {
		super();
	}

	public Type1ReportJasper(String monthName, String monthlyFee, String staticIpFee, String createdBy,
			String reportName, String year, String businessLineCalls, String rebate, String rebate1, String rebate2,
			String overallTotal, Map<Integer, String> manualEntry, Map<Integer, String> manualEntryName,
			String totalData) {
		super();
		this.monthName = monthName;
		this.monthlyFee = monthlyFee;
		this.staticIpFee = staticIpFee;
		this.createdBy = createdBy;
		this.reportName = reportName;
		this.year = year;
		this.businessLineCalls = businessLineCalls;
		this.rebate = rebate;
		this.rebate1 = rebate1;
		this.rebate2 = rebate2;
		this.overallTotal = overallTotal;
		this.manualEntry = manualEntry;
		this.manualEntryName = manualEntryName;
		this.totalData = totalData;
	}

	public String getTotalData() {
		return totalData;
	}

	public void setTotalData(String totalData) {
		this.totalData = totalData;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public String getMonthlyFee() {
		return monthlyFee;
	}

	public void setMonthlyFee(String monthlyFee) {
		this.monthlyFee = monthlyFee;
	}

	public String getStaticIpFee() {
		return staticIpFee;
	}

	public void setStaticIpFee(String staticIpFee) {
		this.staticIpFee = staticIpFee;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getBusinessLineCalls() {
		return businessLineCalls;
	}

	public void setBusinessLineCalls(String businessLineCalls) {
		this.businessLineCalls = businessLineCalls;
	}

	public String getRebate() {
		return rebate;
	}

	public void setRebate(String rebate) {
		this.rebate = rebate;
	}

	public String getRebate1() {
		return rebate1;
	}

	public void setRebate1(String rebate1) {
		this.rebate1 = rebate1;
	}

	public String getRebate2() {
		return rebate2;
	}

	public void setRebate2(String rebate2) {
		this.rebate2 = rebate2;
	}

	public String getOverallTotal() {
		return overallTotal;
	}

	public void setOverallTotal(String overallTotal) {
		this.overallTotal = overallTotal;
	}

	public Map<Integer, String> getManualEntry() {
		return manualEntry;
	}

	public void setManualEntry(Map<Integer, String> manualEntry) {
		this.manualEntry = manualEntry;
	}

	public Map<Integer, String> getManualEntryName() {
		return manualEntryName;
	}

	public void setManualEntryName(Map<Integer, String> manualEntryName) {
		this.manualEntryName = manualEntryName;
	}

}
