package com.calypso.java.practice;

public class CalypsoChild extends CalypsoParent implements CalypsoI {
//overridding class it will go to check your child class of overriding classes
	public int calculate(int s, int t)
	{
		//super keyword is from parent class
		int data=super.calculate(3, 4);
		return s*t*data;
		
	}
	//overloading parameter will change 
	public int calculate(int r)
	{
		
		return r+1;
	}
	//method name starts with lowercase word
	@Override
	public void sayHello() {
		// TODO Auto-generated method stub
		System.out.println("Hello");
	}
	//override this string function override child and parent classes
	public String toString()
	{
		//this keyword means non static resource object..current class...only not static (object )will not show anything
		return this.city;
	}
	
	
	
}
