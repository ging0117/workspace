package com.calypso.training.user;

import com.calypso.training.person.PersonManager;

/**
 * @since 22 NOV 2015
 * @author ging0117
 * @version 1.0
 */
/**
 * 
 * @author ging0117
 *
 */
    public class UserManager {
	private static int firstName;
	//share resource..attached class and share all resources for each class
	private static int ssn;
	private String state;
	//private int age;
	private int age3;
	//can not change value
	public static final String COUNTRY="USA";
	
	public UserManager(){
		
	
		
	}
	//Parameterized constructor
	
	public UserManager(int age){
		
		//this.age= age;
		age3=age;
	}
	/**
	 * Calculate method for returning sum of two numbers
	 * @param num1 int
	 * @param num2 int
	 * @return int Sum of two numbers
	 */
	
	public int calculate(int num1, int num2)
	{
		
	//	return num1+num2+age;
		return num1+num2+age3;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		firstName=24;
		//Object using default constructor
		UserManager userObj = new UserManager();
        int sum =userObj.calculate(32, 18);
        System.out.println("Sum of two numbers:"+ sum);
        userObj.ssn=789;
        userObj.state="CA";
        System.out.println("SSN::"+userObj.ssn);
        System.out.println("state::"+userObj.state);
        UserManager userObj2= new UserManager();
       
        System.out.println("SSN2::"+userObj2.ssn);
        
        //non static--- using heap memory...attached object so its not sharing resource--non static
        System.out.println("state2::"+userObj2.state);
        PersonManager personObj= new PersonManager();
        personObj.calculateUser();
        //calling parameterized constructor
        
        UserManager userobj3= new UserManager(77);
        int sum3=userobj3.calculate(15, 23);
        System.out.println("Sum of two numbers with age::"+sum3);
       //object ...new keyword
        //class--- the first word is capital word
        UserManager use1=new UserManager();
     //   use1.COUNTRY="USA";
        System.out.println(use1.COUNTRY);
        //default....memeory address
        System.out.println("UserObj3 Object::"+userobj3);
        System.out.println("UserObj3 Object::"+userObj);
	}

}
