package com.calypso.java.singleton;

public class SingletonExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserSingleton obj = UserSingleton.getObject();
		obj.city = "Fremont";
		System.out.println(obj.city);
		UserSingleton obj2 = UserSingleton.getObject();
		System.out.println(obj2.city);
	}

}
