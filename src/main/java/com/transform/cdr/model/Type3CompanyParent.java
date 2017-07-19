package com.transform.cdr.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "type3companyparent")
public class Type3CompanyParent {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "overall_total")
	private double overallTotal;

	public Type3CompanyParent(int id, double overallTotal) {
		super();
		this.id = id;
		this.overallTotal = overallTotal;		
	}

	public Type3CompanyParent() {
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
}
