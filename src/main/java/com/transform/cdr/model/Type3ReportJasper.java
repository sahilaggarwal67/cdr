package com.transform.cdr.model;

import java.io.Serializable;
import java.util.Map;

public class Type3ReportJasper implements Serializable {

	private String feeCharges;

	private String mappingName;

	private String traffic;

	private String name;

	private String accountType;

	private String invoiceNo;

	private String invoiceDate;

	private String invoiceSubtotal;

	private String vat;

	private String other;

	private String total;

	private Map<Integer, String> descriptionMap;

	private Map<Integer, String> amountMap;

	private String companyName;

	public Type3ReportJasper() {
		super();
	}

	public Type3ReportJasper(String feeCharges, String mappingName, String traffic, String name, String accountType,
			String invoiceNo, String invoiceDate, String invoiceSubtotal, String vat, String other, String total,
			Map<Integer, String> descriptionMap, Map<Integer, String> amountMap, String companyName) {
		super();
		this.feeCharges = feeCharges;
		this.mappingName = mappingName;
		this.traffic = traffic;
		this.name = name;
		this.accountType = accountType;
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.invoiceSubtotal = invoiceSubtotal;
		this.vat = vat;
		this.other = other;
		this.total = total;
		this.descriptionMap = descriptionMap;
		this.amountMap = amountMap;
		this.companyName = companyName;
	}

	public String getFeeCharges() {
		return feeCharges;
	}

	public void setFeeCharges(String feeCharges) {
		this.feeCharges = feeCharges;
	}

	public String getMappingName() {
		return mappingName;
	}

	public void setMappingName(String mappingName) {
		this.mappingName = mappingName;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceSubtotal() {
		return invoiceSubtotal;
	}

	public void setInvoiceSubtotal(String invoiceSubtotal) {
		this.invoiceSubtotal = invoiceSubtotal;
	}

	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public Map<Integer, String> getDescriptionMap() {
		return descriptionMap;
	}

	public void setDescriptionMap(Map<Integer, String> descriptionMap) {
		this.descriptionMap = descriptionMap;
	}

	public Map<Integer, String> getAmountMap() {
		return amountMap;
	}

	public void setAmountMap(Map<Integer, String> amountMap) {
		this.amountMap = amountMap;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
