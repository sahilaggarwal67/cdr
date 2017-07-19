package com.transform.cdr.model;

import java.io.Serializable;

public class Type3OverallDTO implements Serializable {

	private String invoiceSummary;
	private String invoiceDate;
	private String vesselName;
	private String description;
	private String quantity;
	private String amount;
	private String currency;
	private String amount2;
	private String invoiceSubtotal;
	private String vat;
	private String total;
	private String invoiceNo;
	private String companyName;

	public Type3OverallDTO(String invoiceSummary, String invoiceDate, String vesselName, String description,
			String quantity, String amount, String currency, String amount2, String invoiceSubtotal, String vat,
			String total, String invoiceNo, String companyName) {
		super();
		this.invoiceSummary = invoiceSummary;
		this.invoiceDate = invoiceDate;
		this.vesselName = vesselName;
		this.description = description;
		this.quantity = quantity;
		this.amount = amount;
		this.currency = currency;
		this.amount2 = amount2;
		this.invoiceSubtotal = invoiceSubtotal;
		this.vat = vat;
		this.total = total;
		this.invoiceNo = invoiceNo;
		this.companyName = companyName;
	}

	public Type3OverallDTO() {
		super();
	}

	public String getInvoiceSummary() {
		return invoiceSummary;
	}

	public void setInvoiceSummary(String invoiceSummary) {
		this.invoiceSummary = invoiceSummary;
	}

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAmount2() {
		return amount2;
	}

	public void setAmount2(String amount2) {
		this.amount2 = amount2;
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

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
