package com.transform.cdr.model;

import java.util.List;

import com.transform.cdr.util.AccountType;

public class ShipControllerModel {
	private Ship ship;
	private List<Company> companies;
	private List<Ship> shipList;
	private int selectedCompany;
	private List<AccountType> accountTypes;

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public List<Ship> getShipList() {
		return shipList;
	}

	public void setShipList(List<Ship> shipList) {
		this.shipList = shipList;
	}

	public ShipControllerModel() {
		super();
	}

	public ShipControllerModel(Ship ship, List<Company> companies, List<Ship> shipList, int selectedCompany,
			List<AccountType> accountTypes) {
		super();
		this.ship = ship;
		this.companies = companies;
		this.shipList = shipList;
		this.selectedCompany = selectedCompany;
		this.accountTypes = accountTypes;
	}

	public int getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(int selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public List<AccountType> getAccountTypes() {
		return accountTypes;
	}

	public void setAccountTypes(List<AccountType> accountTypes) {
		this.accountTypes = accountTypes;
	}

}
