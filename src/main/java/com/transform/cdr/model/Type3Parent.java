package com.transform.cdr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "type3parent")
public class Type3Parent {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "parent_id")
	private int parentId;

	@Column(name = "company_id")
	private int companyId;

	@Column(name = "overall_total")
	private double overallTotal;

	@Column(name = "format")
	private int format;

	public Type3Parent(int id, int companyId, double overallTotal, int parentId, int format) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.overallTotal = overallTotal;
		this.parentId = parentId;
		this.format = format;
	}

	public Type3Parent() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getOverallTotal() {
		return overallTotal;
	}

	public void setOverallTotal(double overallTotal) {
		this.overallTotal = overallTotal;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getFormat() {
		return format;
	}

	public void setFormat(int format) {
		this.format = format;
	}

}
