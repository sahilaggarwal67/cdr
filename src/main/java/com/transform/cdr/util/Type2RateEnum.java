package com.transform.cdr.util;

public enum Type2RateEnum {
	Price1("Cellular", 1), Price2("Teresstial", 2),NoRate("NoRate",3);

	String priceType;
	int type;

	Type2RateEnum(String priceType, int type) {
		this.type = type;
		this.priceType = priceType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

}
