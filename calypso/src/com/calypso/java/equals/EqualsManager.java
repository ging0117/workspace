package com.calypso.java.equals;

public class EqualsManager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserEquals obj = new UserEquals();
		obj.name = "F";
		obj.ssn = 24;
		UserEquals obj2 = new UserEquals();
		obj2.name = "f";
		obj2.ssn = 36;
		System.out.println(obj.equals(obj2));
		//if only one & it will still compare another one
		if(obj.name.equals(obj2.name)&&(obj.ssn==obj2.ssn))
		{
			System.out.println("Equal");
		}else{
			
			
			System.out.println("NON");
		}
	}

}
