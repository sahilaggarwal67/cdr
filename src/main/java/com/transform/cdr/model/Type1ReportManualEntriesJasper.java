package com.transform.cdr.model;

import java.io.Serializable;

public class Type1ReportManualEntriesJasper implements Serializable {
	private String entryName;
	private double entryValue;

	public String getEntryName() {
		return entryName;
	}

	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}

	public double getEntryValue() {
		return entryValue;
	}

	public void setEntryValue(double entryValue) {
		this.entryValue = entryValue;
	}

	public Type1ReportManualEntriesJasper(String entryName, double entryValue) {
		super();
		this.entryName = entryName;
		this.entryValue = entryValue;
	}

	public Type1ReportManualEntriesJasper() {
		super();
	}

}
