package com.transform.cdr.model;

import java.io.Serializable;

public class Type1ReportDataJasper implements Serializable {

	private String time;
	private String network;
	private String from_msisdn;
	private String to_msisdn;
	private String duration;
	private String price;
	private double rate;
	private String headerData;

	public Type1ReportDataJasper(String time, String network, String from_msisdn, String to_msisdn, String duration,
			String price, double rate, String headerData) {
		super();
		this.time = time;
		this.network = network;
		this.from_msisdn = from_msisdn;
		this.to_msisdn = to_msisdn;
		this.duration = duration;
		this.price = price;
		this.rate = rate;
		this.headerData = headerData;
	}

	public Type1ReportDataJasper() {
		super();
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getHeaderData() {
		return headerData;
	}

	public void setHeaderData(String headerData) {
		this.headerData = headerData;
	}

}
