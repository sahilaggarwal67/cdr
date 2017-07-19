package com.transform.cdr.model;

import java.io.Serializable;

public class FilesUploaded implements Serializable {
	private String type;
	private int reportId;
	private String uploadedTime;
	private String uploadedBy;
	private String fileName;

	public FilesUploaded() {
		super();
	}

	public FilesUploaded(String type, int reportId, String uploadedTime, String uploadedBy, String fileName) {
		super();
		this.type = type;
		this.reportId = reportId;
		this.uploadedTime = uploadedTime;
		this.uploadedBy = uploadedBy;
		this.fileName = fileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public String getUploadedTime() {
		return uploadedTime;
	}

	public void setUploadedTime(String uploadedTime) {
		this.uploadedTime = uploadedTime;
	}

	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
