package com.transform.cdr.util;

import java.util.HashMap;
import java.util.Map;

public enum AccountType {
	Type_1("Type 1", 1), Type_2("Type 2", 2), CITADEL("Citadel", 3), FULL("Full", 4);
	private String accountType;
	private int accountCode;

	AccountType(String accountType, int accountCode) {
		this.accountCode = accountCode;
		this.accountType = accountType;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public int getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(int accountCode) {
		this.accountCode = accountCode;
	}

}
