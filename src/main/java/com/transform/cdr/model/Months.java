package com.transform.cdr.model;

import java.io.Serializable;

public class Months implements Serializable{
	private int id;
	private String name;

	public Months() {
		super();
	}

	public Months(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
