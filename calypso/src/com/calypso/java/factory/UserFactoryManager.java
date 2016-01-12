package com.calypso.java.factory;

public class UserFactoryManager {
	
	public static  void main (String[] args)
	{
		UserFactoryI obj = UserFactory.getObject("Add");
		obj.printdata();
		UserFactoryI obj2 = UserFactory.getObject("dffdfd");
		obj2.printdata();
		
	}
	

}
