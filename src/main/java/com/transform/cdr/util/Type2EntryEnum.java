package com.transform.cdr.util;

public enum Type2EntryEnum {
	CrewDataMb(11), VoIPDataUseMb(12), BusinessDataMb(13), OtherDataUseMb(14), CrewVoipMinutes(15);

	private int id;

	Type2EntryEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
