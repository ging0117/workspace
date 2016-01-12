package com.calypso.java.singleton;

public class UserSingleton {
	public static UserSingleton obj;
	public String city;
	private UserSingleton (){
		
		
		
	}
	public static UserSingleton getObject()
	{
		//check if it is null
		if(obj==null){
			obj=new UserSingleton();
			
		}
		return obj;
		
		
	}
	
	

}
