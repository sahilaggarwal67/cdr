package com.transform.cdr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "type3reportdata")
public class Type3ReportData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "report_id")
	private int reportId;

	@Column(name = "item")
	private String item;

	@Column(name = "desciption")
	private String description;

	@Column(name = "units")
	private double units;

	@Column(name = "cost_per_unit")
	private double costPerUnit;

	@Column(name = "amount")
	private double amount;

	public Type3ReportData() {
		super();
		this.item = "";
	}

	public Type3ReportData(int id, int reportId, String item, String description, double units, double costPerUnit,
			double amount) {
		super();
		this.id = id;
		this.reportId = reportId;
		this.description = description;
		this.units = units;
		this.costPerUnit = costPerUnit;
		this.amount = amount;
		this.item = item;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getUnits() {
		return units;
	}

	public void setUnits(double units) {
		this.units = units;
	}

	public double getCostPerUnit() {
		return costPerUnit;
	}

	public void setCostPerUnit(double costPerUnit) {
		this.costPerUnit = costPerUnit;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

}
