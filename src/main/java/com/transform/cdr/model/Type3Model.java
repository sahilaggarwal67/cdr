package com.transform.cdr.model;

import java.util.List;
import java.util.Map;

public class Type3Model {
	private Integer format;
	private List<String> reportPaths;
	private Map<String, Ship> shipMap;

	public List<String> getReportPaths() {
		return reportPaths;
	}

	public void setReportPaths(List<String> reportPaths) {
		this.reportPaths = reportPaths;
	}

	public Map<String, Ship> getShipMap() {
		return shipMap;
	}

	public void setShipMap(Map<String, Ship> shipMap) {
		this.shipMap = shipMap;
	}

	public Integer getFormat() {
		return format;
	}

	public void setFormat(Integer format) {
		this.format = format;
	}

}
