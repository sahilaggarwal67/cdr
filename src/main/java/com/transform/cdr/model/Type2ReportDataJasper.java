package com.transform.cdr.model;

public class Type2ReportDataJasper {

	private String cdrId;

	private String msisdn;

	private String imsi;

	private String callStart;

	private String description;

	private String bNumber;

	private String bundleIndicator;

	private String unitPrice;

	private String quantityCharged;

	private String callType;

	private String ispPrice;

	private String mobileNumber;

	private String otherPartyMsisdn;

	private String duration;

	private String callDate;

	private String callTime;

	private String totalPrice;

	private String price1;

	private String price2;

	public Type2ReportDataJasper() {
		super();
	}

	public Type2ReportDataJasper(String cdrId, String msisdn, String imsi, String callStart, String description,
			String bNumber, String bundleIndicator, String unitPrice, String quantityCharged, String callType,
			String ispPrice, String mobileNumber, String otherPartyMsisdn, String duration, String callDate,
			String callTime, String totalPrice, String price1, String price2) {
		super();
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

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getQuantityCharged() {
		return quantityCharged;
	}

	public void setQuantityCharged(String quantityCharged) {
		this.quantityCharged = quantityCharged;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public String getIspPrice() {
		return ispPrice;
	}

	public void setIspPrice(String ispPrice) {
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

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getPrice1() {
		return price1;
	}

	public void setPrice1(String price1) {
		this.price1 = price1;
	}

	public String getPrice2() {
		return price2;
	}

	public void setPrice2(String price2) {
		this.price2 = price2;
	}

}
