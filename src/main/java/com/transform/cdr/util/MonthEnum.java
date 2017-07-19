package com.transform.cdr.util;

public enum MonthEnum {
	January(1), Feburary(2), March(3), April(4), May(5), June(6), July(7), August(8), September(9), October(
			10), November(11), December(12);

	int month;

	MonthEnum(int month) {
		this.month = month;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}	

}
