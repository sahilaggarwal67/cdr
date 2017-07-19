package com.transform.cdr.model;

import java.io.Serializable;
import java.util.List;

public class Type1process implements Serializable {
	private Ship ship;
	/*private double voiceRebate;
	private double dataRebate;*/
	private List<ManualFields> entries;
	private List<Type2ShipModel> shipModels;

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public List<ManualFields> getEntries() {
		return entries;
	}

	public void setEntries(List<ManualFields> entries) {
		this.entries = entries;
	}

	public List<Type2ShipModel> getShipModels() {
		return shipModels;
	}

	public void setShipModels(List<Type2ShipModel> shipModels) {
		this.shipModels = shipModels;
	}
	

	/*public double getVoiceRebate() {
		return voiceRebate;
	}

	public void setVoiceRebate(double voiceRebate) {
		this.voiceRebate = voiceRebate;
	}

	public double getDataRebate() {
		return dataRebate;
	}

	public void setDataRebate(double dataRebate) {
		this.dataRebate = dataRebate;
	}*/

	public Type1process() {
		super();
	}

}
