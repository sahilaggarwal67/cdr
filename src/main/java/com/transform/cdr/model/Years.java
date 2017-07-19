package com.transform.cdr.model;

import java.io.Serializable;

public class Years implements Serializable {
	private int id;
	private String name;

	public Years() {
		super();
	}

	public Years(int id, String name) {
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
