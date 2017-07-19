package com.transform.cdr.model;

import java.io.Serializable;

public class Type1ProcessModel implements Serializable {

	private String fileName;
	private String filePath;
	private Type1process type1process;
	private String username;
	private String month;
	private String year;

	public Type1ProcessModel(String fileName, String filePath, Type1process type1process, String username, String month,
			String year) {
		super();
		this.fileName = fileName;
		this.filePath = filePath;
		this.type1process = type1process;
		this.username = username;
		this.month = month;
		this.year = year;
	}

	public Type1ProcessModel() {
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Type1process getType1process() {
		return type1process;
	}

	public void setType1process(Type1process type1process) {
		this.type1process = type1process;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
