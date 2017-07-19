package com.transform.cdr.util;

public enum Type1EntryEnum {
	SatC(1), Email(2), SMS(3), PDN(4), IridiumCitadel(5), IridiumOpenport(6), SeabrowserCards(7), CrewDataMb(
			8), BusinessDataMb(9), CrewVoipMinutes(10);

	private int id;

	Type1EntryEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
