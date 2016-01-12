package com.calypso.training.oops.user;

import com.calypso.training.oops.person.IPerson;
import com.calypso.training.oops.person.Person;

public class User extends Person implements IUser, IPerson {

	public String name;
 
	
	public int calculate(int num1, int num2) {

		return num1 - num2;

	}
	protected int calculate(int num1, int num2,int num3) {

		return num1 - num2+ num3;
	}
		public String calculate(String data) {

			return data;
		}

	
	
	public static void main(String[] args) {

		Person personobj = new Person();

		personobj.ssn = 123;

		System.out.println("total :: " + personobj.calculate(23, 37));

		User userobj = new User();

		userobj.ssn = 345;

		System.out.println("User total :: " + userobj.calculate(28, 30));

		userobj.name = "Peter";

		// Not possible: Jaa parent does not know about child;

		// User useobj2 = new User();

		// Child object in parent object reference

		Person personobj2 = new User();

		System.out.println("Person total :: " + personobj.calculate(23, 32));

		System.out.println("Instance of User::"+(personobj2 instanceof User));
		//Downcasting
		if(personobj2 instanceof User)
		{
			User userObj3=(User)personobj2;
			userObj3.name="Fremont";
			
			userObj3.printData("Fremont");
			userObj3.printResult(24);
			
			System.out.println(User.CITY);
			System.out.println(userObj3.getName());
			System.out.println(userObj3.getAge());
			System.out.println(userObj3.calculate("nework"));
			System.out.println(userObj3.calculate(12, 23, 44));
			System.out.println(userObj3.calculate(23, 31));
		}
		// abstract class can not initialize objects
		
	}

	
	
	//Interface objects cannot be created
	
	//Person per = new IPerson();
	@Override
	public void printData(String name) {
		// TODO Auto-generated method stub
		
		System.out.println("PrintResult::"+name);
		
	}

	@Override
	public void printResult(int id) {
		// TODO Auto-generated method stub
		System.out.println("PrintData::"+id);
	}

	
}