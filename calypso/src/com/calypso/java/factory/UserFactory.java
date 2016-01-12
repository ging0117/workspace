package com.calypso.java.factory;

public class UserFactory {
	public static UserFactoryI getObject(String type) {
		UserFactoryI obj = null;
		if (type.equals("Add")) {

			obj = new Address();

		} else {

			obj = new City();
		}
		return obj;
	}

}
