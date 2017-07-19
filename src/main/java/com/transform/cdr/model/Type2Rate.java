package com.transform.cdr.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "type2rate")
public class Type2Rate {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column(name = "rate_entry_name")
	private String rateEntryName;
	@Column(name = "rate_type")
	private int rateType;
	@Column(name = "multiplier")
	private double multiplier;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRateEntryName() {
		return rateEntryName;
	}

	public void setRateEntryName(String rateEntryName) {
		this.rateEntryName = rateEntryName;
	}

	public int getRateType() {
		return rateType;
	}

	public void setRateType(int rateType) {
		this.rateType = rateType;
	}

	public double getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(double multiplier) {
		this.multiplier = multiplier;
	}

	public Type2Rate() {
		super();
	}

	public Type2Rate(int id, String rateEntryName, int rateType, double multiplier) {
		super();
		this.id = id;
		this.rateEntryName = rateEntryName;
		this.rateType = rateType;
		this.multiplier = multiplier;
	}

}
