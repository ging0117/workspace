package com.calypso.training.Factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UserFactory {
	
	public String name;
	public static UserFactoryI getInstance(String type) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		UserFactoryI obj = null;
		if (type.equals("Add")) {

			obj = new AdminUser();

		} else if(type.equals("General")) {
        obj= new GeneralUser();
			
		}
		
		Method[] method=obj.getClass().getMethods();
		System.out.println(method[0].invoke(obj,24 ));
		
		return obj;
	}

}
