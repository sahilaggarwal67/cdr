package com.transform.cdr.service;

import java.util.List;

import com.transform.cdr.model.Ship;

public interface ShipService {

	public void addShip(Ship ship);

	public List<Ship> getShipList(int companyId);

	public void updateShip(Ship ship);

	public Ship getShipById(int id);

}
