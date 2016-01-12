package com.calypso.training.equalsharshcode;

public class EqualsManager {

	
	
	
	public int id;
	public String name;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		UserEqual userObj= new UserEqual();
		userObj.id=22;
		userObj.name="Fremont";
		UserEqual userObj2= new UserEqual();
		userObj2.id=22;
		userObj2.name="Fremont";
		
		System.out.println("First Object:"+userObj);
		System.out.println("second Object:"+userObj2);
		System.out.println("Comparing Objects:"+userObj.equals(userObj2));
		
		if(userObj.id==userObj2.id&& userObj.name.equals(userObj2.name)){
			System.out.println("Equal");
		}else{
		System.out.println("not equal");		
				
			}
			

		}
	
	}

