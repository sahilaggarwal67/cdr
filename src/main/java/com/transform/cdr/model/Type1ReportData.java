package com.transform.cdr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "type1reportdata")
public class Type1ReportData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column(name = "report_id")
	private int reportId;
	@Column(name = "time")
	private String time;
	@Column(name = "network")
	private String network;
	@Column(name = "from_msisdn")
	private String from_msisdn;
	@Column(name = "to_msisdn")
	private String to_msisdn;
	@Column(name = "duration")
	private String duration;
	@Column(name = "price")
	private double price;
	@Column(name = "rate")
	private double rate;
	@Column(name = "header_id")
	private int headerId;
	@Column(name = "actual_price")
	private double actualPrice;

	public Type1ReportData() {
		super();
	}

	public Type1ReportData(int id, int reportId, String time, String network, String from_msisdn, String to_msisdn,
			String duration, double price, double rate, int headerId, double actualPrice) {
		super();
		this.id = id;
		this.reportId = reportId;
		this.time = time;
		this.network = network;
		this.from_msisdn = from_msisdn;
		this.to_msisdn = to_msisdn;
		this.duration = duration;
		this.price = price;
		this.rate = rate;
		this.headerId = headerId;
		this.actualPrice = actualPrice;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getFrom_msisdn() {
		return from_msisdn;
	}

	public void setFrom_msisdn(String from_msisdn) {
		this.from_msisdn = from_msisdn;
	}

	public String getTo_msisdn() {
		return to_msisdn;
	}

	public void setTo_msisdn(String to_msisdn) {
		this.to_msisdn = to_msisdn;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getHeaderId() {
		return headerId;
	}

	public void setHeaderId(int headerId) {
		this.headerId = headerId;
	}

	public double getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(double actualPrice) {
		this.actualPrice = actualPrice;
	}

}
