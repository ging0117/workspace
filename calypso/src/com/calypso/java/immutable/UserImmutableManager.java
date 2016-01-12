package com.calypso.java.immutable;

public class UserImmutableManager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserImmutable obj = new UserImmutable("USA");
		System.out.println(obj.getCountry());
		UserImmutable obj1 = new UserImmutable("england");
		System.out.println(obj1.getCountry());
		
	}

}
