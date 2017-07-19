package com.transform.cdr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "type3report")
public class Type3Report {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "ship_id")
	private int shipId;

	@Column(name = "report_name")
	private String reportName;

	@Column(name = "fee_charges")
	private double feeCharges;

	@Column(name = "mapping_name")
	private String mappingName;

	@Column(name = "type3_report_id")
	private int type3ReportId;

	@Column(name = "invoice_no")
	private String invoiceNo;

	@Column(name = "invoice_date")
	private String invoiceDate;

	@Column(name = "invoice_subtotal")
	private double invoiceSubtotal;

	@Column(name = "vat")
	private double vat;

	@Column(name = "other")
	private double other;

	@Column(name = "total")
	private double total;

	@Transient
	private int companyId;

	public Type3Report() {
		super();
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

	public Type3Report(int id, int shipId, String reportName, double feeCharges, String mappingName, int type3ReportId,
			String invoiceNo, String invoiceDate, double invoiceSubtotal, double vat, double other, double total) {
		super();
		this.id = id;
		this.shipId = shipId;
		this.reportName = reportName;
		this.feeCharges = feeCharges;
		this.mappingName = mappingName;
		this.type3ReportId = type3ReportId;
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.invoiceSubtotal = invoiceSubtotal;
		this.vat = vat;
		this.other = other;
		this.total = total;
	}

	public String getMappingName() {
		return mappingName;
	}

	public void setMappingName(String mappingName) {
		this.mappingName = mappingName;
	}

	public int getType3ReportId() {
		return type3ReportId;
	}

	public void setType3ReportId(int type3ReportId) {
		this.type3ReportId = type3ReportId;
	}

	public double getFeeCharges() {
		return feeCharges;
	}

	public void setFeeCharges(double feeCharges) {
		this.feeCharges = feeCharges;
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

	public double getInvoiceSubtotal() {
		return invoiceSubtotal;
	}

	public void setInvoiceSubtotal(double invoiceSubtotal) {
		this.invoiceSubtotal = invoiceSubtotal;
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}

	public double getOther() {
		return other;
	}

	public void setOther(double other) {
		this.other = other;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

}
