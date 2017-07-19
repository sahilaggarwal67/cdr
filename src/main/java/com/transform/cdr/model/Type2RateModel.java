package com.transform.cdr.model;

import java.util.List;

import com.transform.cdr.util.Type2RateEnum;

public class Type2RateModel {

	private String rateEntryName;
	private int rateType;
	private double multiplier;
	private List<Type2RateEnum> priceTypeList;

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

	public List<Type2RateEnum> getPriceTypeList() {
		return priceTypeList;
	}

	public void setPriceTypeList(List<Type2RateEnum> priceTypeList) {
		this.priceTypeList = priceTypeList;
	}

	public Type2RateModel() {
		super();
	}

	public Type2RateModel(String rateEntryName, int rateType, double multiplier, List<Type2RateEnum> priceTypeList) {
		super();
		this.rateEntryName = rateEntryName;
		this.rateType = rateType;
		this.multiplier = multiplier;
		this.priceTypeList = priceTypeList;
	}

}
