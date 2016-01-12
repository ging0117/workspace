package com.calypso.java.prototype;

public class AddressPrototype {

	public static void main(String[] args) throws CloneNotSupportedException {

		Address obj = new Address();

		obj.name = "John";
		System.out.println("obj"+ obj.name);
		Address obj2= new Address();
		System.out.println("obj2"+ obj2.name);
		
		//downcasting
		Address obj3 =(Address) obj.clone();
		
		System.out.println("obj2"+ obj3.name);
		
	}
}
