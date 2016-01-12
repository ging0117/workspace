package com.calypso.java.prototype;

public class Address implements Cloneable {

	public Object clone() throws CloneNotSupportedException {

		return super.clone();
	}

	public String name;
}