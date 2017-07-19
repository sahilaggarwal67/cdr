package com.transform.cdr.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "type1report")
public class Type1Report {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column(name = "ship_id")
	private int shipId;
	@Column(name = "report_name")
	private String reportName;
	@Column(name = "monthly_fee")
	private double monthlyFee;
	@Column(name = "static_ip_fee")
	private double staticIpFee;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_time")
	private Calendar createdTime;
	@Column(name = "month")
	private String month;
	@Column(name = "year")
	private String year;
	@Column(name = "business_line_calls")
	private double businessLineCalls;
	@Column(name = "rebate")
	private double rebate;
	@Column(name = "rebate1")
	private double rebate1;
	@Column(name = "rebate2")
	private double rebate2;
	@Column(name = "overall_total")
	private double overallTotal;
	@Column(name = "iridium_citadel_monthly_fee")
	private double iridiumCitadelMonthlyFee;
	@Column(name = "type2_report_id")
	private int type2ReportId;

	@Column(name = "voice_rebate")
	private double voiceRebate;
	@Column(name = "data_rebate")
	private double dataRebate;

	public Type1Report() {
		super();
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Type1Report(int id, int shipId, String reportName, double monthlyFee, double staticIpFee, String createdBy,
			Calendar createdTime, String month, String year, double businessLineCalls, double rebate, double rebate1,
			double rebate2, double overallTotal, double iridiumCitadelMonthlyFee, int type2ReportId, double voiceRebate,
			double dataRebate) {
		super();
		this.id = id;
		this.shipId = shipId;
		this.reportName = reportName;
		this.monthlyFee = monthlyFee;
		this.staticIpFee = staticIpFee;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.month = month;
		this.year = year;
		this.businessLineCalls = businessLineCalls;
		this.rebate = rebate;
		this.rebate1 = rebate1;
		this.rebate2 = rebate2;
		this.overallTotal = overallTotal;
		this.iridiumCitadelMonthlyFee = iridiumCitadelMonthlyFee;
		this.type2ReportId = type2ReportId;
		this.voiceRebate = voiceRebate;
		this.dataRebate = dataRebate;
	}

	public Calendar getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Calendar createdTime) {
		this.createdTime = createdTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getShipId() {
		return shipId;
	}

	public void setShipId(int shipId) {
		this.shipId = shipId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public double getMonthlyFee() {
		return monthlyFee;
	}

	public void setMonthlyFee(double monthlyFee) {
		this.monthlyFee = monthlyFee;
	}

	public double getStaticIpFee() {
		return staticIpFee;
	}

	public void setStaticIpFee(double staticIpFee) {
		this.staticIpFee = staticIpFee;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public double getBusinessLineCalls() {
		return businessLineCalls;
	}

	public void setBusinessLineCalls(double businessLineCalls) {
		this.businessLineCalls = businessLineCalls;
	}

	public double getRebate() {
		return rebate;
	}

	public void setRebate(double rebate) {
		this.rebate = rebate;
	}

	public double getRebate1() {
		return rebate1;
	}

	public void setRebate1(double rebate1) {
		this.rebate1 = rebate1;
	}

	public double getRebate2() {
		return rebate2;
	}

	public void setRebate2(double rebate2) {
		this.rebate2 = rebate2;
	}

	public double getOverallTotal() {
		return overallTotal;
	}

	public void setOverallTotal(double overallTotal) {
		this.overallTotal = overallTotal;
	}

	public double getIridiumCitadelMonthlyFee() {
		return iridiumCitadelMonthlyFee;
	}

	public void setIridiumCitadelMonthlyFee(double iridiumCitadelMonthlyFee) {
		this.iridiumCitadelMonthlyFee = iridiumCitadelMonthlyFee;
	}

	public int getType2ReportId() {
		return type2ReportId;
	}

	public void setType2ReportId(int type2ReportId) {
		this.type2ReportId = type2ReportId;
	}

	public double getVoiceRebate() {
		return voiceRebate;
	}

	public void setVoiceRebate(double voiceRebate) {
		this.voiceRebate = voiceRebate;
	}

	public double getDataRebate() {
		return dataRebate;
	}

	public void setDataRebate(double dataRebate) {
		this.dataRebate = dataRebate;
	}

}
