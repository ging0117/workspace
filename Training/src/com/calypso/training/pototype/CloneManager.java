package com.calypso.training.pototype;

public class CloneManager {

	public static void main(String[] args) throws CloneNotSupportedException {
		// TODO Auto-generated method stub

		Cloneable obj= new Cloneable();
		obj.name="Fremont";
		System.out.println("First time"+obj.name);
		Cloneable obj2 = new Cloneable();
		System.out.println("Seceond time"+ obj2.name);
		Cloneable obj3 = (Cloneable) obj.clone();
		System.out.println("Third time"+ obj3.name);
		
		
		
	}

}
