package com.transform.cdr.model;

import java.io.Serializable;
import java.util.List;

public class Type2ShipModel implements Serializable {
	private Ship ship;
	/*private double voiceRebate;
	private double dataRebate;*/
	private List<ManualFields> manualFields;

	public Type2ShipModel() {
		super();
	}

	public Type2ShipModel(Ship ship, List<ManualFields> manualFields) {
		super();
		this.ship = ship;
		this.manualFields = manualFields;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public List<ManualFields> getManualFields() {
		return manualFields;
	}

	public void setManualFields(List<ManualFields> manualFields) {
		this.manualFields = manualFields;
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

}
