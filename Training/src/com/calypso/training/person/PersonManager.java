package com.calypso.training.person;

import com.calypso.training.user.UserManager;

public class PersonManager {
public void calculateUser(){
	UserManager userObj= new UserManager();
	int sum= userObj.calculate(10, 18);
//	userObj.calculate(num1, num2)
	System.out.println("Sum from PM:: "+sum);
	
}
	
	
}
