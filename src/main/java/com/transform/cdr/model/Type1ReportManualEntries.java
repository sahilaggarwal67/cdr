package com.transform.cdr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "type1reportmanualentries")
public class Type1ReportManualEntries implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column(name = "report_id")
	private int reportId;
	@Column(name = "manual_entry_id")
	private int manualEntryId;
	@Column(name = "manual_entry_unit")
	private double manualEntryUnit;
	@Column(name = "manual_entry_cost_per_unit")
	private double manualEntryCostPerUnit;
	@Column(name = "manual_entry_value")
	private double manualEntryValue;

	public Type1ReportManualEntries() {
		super();
	}

	public Type1ReportManualEntries(int id, int reportId, int manualEntryId, double manualEntryUnit,
			double manualEntryCostPerUnit, double manualEntryValue) {
		super();
		this.id = id;
		this.reportId = reportId;
		this.manualEntryId = manualEntryId;
		this.manualEntryUnit = manualEntryUnit;
		this.manualEntryCostPerUnit = manualEntryCostPerUnit;
		this.manualEntryValue = manualEntryValue;
	}

	public double getManualEntryValue() {
		return manualEntryValue;
	}

	public void setManualEntryValue(double manualEntryValue) {
		this.manualEntryValue = manualEntryValue;
	}

	public double getManualEntryUnit() {
		return manualEntryUnit;
	}

	public void setManualEntryUnit(double manualEntryUnit) {
		this.manualEntryUnit = manualEntryUnit;
	}

	public double getManualEntryCostPerUnit() {
		return manualEntryCostPerUnit;
	}

	public void setManualEntryCostPerUnit(double manualEntryCostPerUnit) {
		this.manualEntryCostPerUnit = manualEntryCostPerUnit;
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

	public int getManualEntryId() {
		return manualEntryId;
	}

	public void setManualEntryId(int manualEntryId) {
		this.manualEntryId = manualEntryId;
	}

}
