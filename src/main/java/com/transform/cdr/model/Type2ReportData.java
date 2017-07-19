package com.transform.cdr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "type2reportdata")
public class Type2ReportData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column(name = "report_id")
	private int reportId;

	@Column(name = "cdr_id")
	private String cdrId;

	@Column(name = "msisdn")
	private String msisdn;
	@Column(name = "imsi")
	private String imsi;
	@Column(name = "call_start")
	private String callStart;
	@Column(name = "description")
	private String description;
	@Column(name = "b_number")
	private String bNumber;
	@Column(name = "bundle_indicator")
	private String bundleIndicator;
	@Column(name = "unit_price")
	private double unitPrice;
	@Column(name = "quantity_charged")
	private double quantityCharged;
	@Column(name = "call_type")
	private String callType;
	@Column(name = "isp_price")
	private double ispPrice;
	@Column(name = "mobile_number")
	private String mobileNumber;
	@Column(name = "other_party_msisdn")
	private String otherPartyMsisdn;
	@Column(name = "duration")
	private String duration;
	@Column(name = "call_date")
	private String callDate;
	@Column(name = "call_time")
	private String callTime;
	@Column(name = "total_price")
	private double totalPrice;
	@Column(name = "price1")
	private double price1;
	@Column(name = "price2")
	private double price2;
	@Column(name = "billing_period")
	private String billingPeriod;
	@Column(name = "event_id")
	private String eventId;
	@Column(name = "show_in_report")
	private int showInReport;
	@Column(name = "quantity")
	private double quantity;
	@Column(name = "record_no")
	private int recordNo;

	public Type2ReportData() {
		super();
	}

	public Type2ReportData(int id, int reportId, String cdrId, String msisdn, String imsi, String callStart,
			String description, String bNumber, String bundleIndicator, double unitPrice, double quantityCharged,
			String callType, double ispPrice, String mobileNumber, String otherPartyMsisdn, String duration,
			String callDate, String callTime, double totalPrice, double price1, double price2, String billingPeriod,
			String eventId, int showInReport, double quantity, int recordNo) {
		super();
		this.id = id;
		this.reportId = reportId;
		this.cdrId = cdrId;
		this.msisdn = msisdn;
		this.imsi = imsi;
		this.callStart = callStart;
		this.description = description;
		this.bNumber = bNumber;
		this.bundleIndicator = bundleIndicator;
		this.unitPrice = unitPrice;
		this.quantityCharged = quantityCharged;
		this.callType = callType;
		this.ispPrice = ispPrice;
		this.mobileNumber = mobileNumber;
		this.otherPartyMsisdn = otherPartyMsisdn;
		this.duration = duration;
		this.callDate = callDate;
		this.callTime = callTime;
		this.totalPrice = totalPrice;
		this.price1 = price1;
		this.price2 = price2;
		this.billingPeriod = billingPeriod;
		this.eventId = eventId;
		this.showInReport = showInReport;
		this.quantity = quantity;
		this.recordNo = recordNo;
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

	public String getCdrId() {
		return cdrId;
	}

	public void setCdrId(String cdrId) {
		this.cdrId = cdrId;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getCallStart() {
		return callStart;
	}

	public void setCallStart(String callStart) {
		this.callStart = callStart;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getbNumber() {
		return bNumber;
	}

	public void setbNumber(String bNumber) {
		this.bNumber = bNumber;
	}

	public String getBundleIndicator() {
		return bundleIndicator;
	}

	public void setBundleIndicator(String bundleIndicator) {
		this.bundleIndicator = bundleIndicator;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getQuantityCharged() {
		return quantityCharged;
	}

	public void setQuantityCharged(double quantityCharged) {
		this.quantityCharged = quantityCharged;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public double getIspPrice() {
		return ispPrice;
	}

	public void setIspPrice(double ispPrice) {
		this.ispPrice = ispPrice;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getOtherPartyMsisdn() {
		return otherPartyMsisdn;
	}

	public void setOtherPartyMsisdn(String otherPartyMsisdn) {
		this.otherPartyMsisdn = otherPartyMsisdn;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getCallDate() {
		return callDate;
	}

	public void setCallDate(String callDate) {
		this.callDate = callDate;
	}

	public String getCallTime() {
		return callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getPrice1() {
		return price1;
	}

	public void setPrice1(double price1) {
		this.price1 = price1;
	}

	public double getPrice2() {
		return price2;
	}

	public void setPrice2(double price2) {
		this.price2 = price2;
	}

	public String getBillingPeriod() {
		return billingPeriod;
	}

	public void setBillingPeriod(String billingPeriod) {
		this.billingPeriod = billingPeriod;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public int getShowInReport() {
		return showInReport;
	}

	public void setShowInReport(int showInReport) {
		this.showInReport = showInReport;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public int getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(int recordNo) {
		this.recordNo = recordNo;
	}

}
