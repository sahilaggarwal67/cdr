package com.transform.cdr.model;

import java.io.Serializable;
import java.util.List;

public class File implements Serializable {

	private String year;
	private String month;
	private List<String> years;
	private List<String> months;
	private List<FilesUploaded> filesUploaded;

	public File() {
		super();
	}

	public File(String year, String month, List<String> years, List<String> months) {
		super();
		this.year = year;
		this.month = month;
		this.years = years;
		this.months = months;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public List<String> getYears() {
		return years;
	}

	public void setYears(List<String> years) {
		this.years = years;
	}

	public List<String> getMonths() {
		return months;
	}

	public void setMonths(List<String> months) {
		this.months = months;
	}

	public List<FilesUploaded> getFilesUploaded() {
		return filesUploaded;
	}

	public void setFilesUploaded(List<FilesUploaded> filesUploaded) {
		this.filesUploaded = filesUploaded;
	}

}
