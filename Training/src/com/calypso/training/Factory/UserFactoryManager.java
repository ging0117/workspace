package com.calypso.training.Factory;

import java.lang.reflect.InvocationTargetException;

public class UserFactoryManager {

	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException  {
		// TODO Auto-generated method stub
		
		UserFactoryI userObj= UserFactory.getInstance("Add");
		System.out.println("autheticate admin :: "+userObj.authenticate(11111));
		userObj=UserFactory.getInstance("General");
		System.out.println("authenticate general::"+userObj.authenticate(2345));

	}

}
