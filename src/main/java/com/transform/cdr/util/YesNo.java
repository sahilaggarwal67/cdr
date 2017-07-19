package com.transform.cdr.util;

public enum YesNo {
	Yes("Yes", 1), No("No", 0);

	String label;
	int value;

	YesNo(String label, int value) {
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
