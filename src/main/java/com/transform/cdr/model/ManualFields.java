package com.transform.cdr.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "manual_fields")
public class ManualFields implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column(name = "account_type")
	private int accountType;
	@Column(name = "entry_name")
	private String entryName;

	@Transient
	private double entryUnits;

	@Transient
	private double entryCostPerUnit;

	public ManualFields() {
		super();
	}

	public ManualFields(int id, int accountType, String entryName) {
		super();
		this.id = id;
		this.accountType = accountType;
		this.entryName = entryName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public String getEntryName() {
		return entryName;
	}

	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}

	public double getEntryUnits() {
		return entryUnits;
	}

	public void setEntryUnits(double entryUnits) {
		this.entryUnits = entryUnits;
	}

	public double getEntryCostPerUnit() {
		return entryCostPerUnit;
	}

	public void setEntryCostPerUnit(double entryCostPerUnit) {
		this.entryCostPerUnit = entryCostPerUnit;
	}

}
