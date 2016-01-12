package com.calypso.java.threads;

public class Calculator extends 	Thread {
	private int count;
	public Calculator(int counter){
		
		this.count=counter;
		
	}
	
	//I/O 
	public void run()
	{
		int sum=0;
		for(int i=0;i<=count;i++)
		{
			
			
			sum+=i;
		}
		
		System.out.println("total is: "+	sum+"for:"+ Thread.currentThread());
		
	}
	
	
	

}
