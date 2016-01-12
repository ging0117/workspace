package com.calypso.training.map;

public class Customer implements Comparable<Customer> {
	
	public int ssn;
	public String name;
	
	public Customer(String n, int s)
	{
		
		this.name=n;
		this.ssn=s;
		
	}

	@Override
	public int compareTo(Customer o) {
		// TODO Auto-generated method stub
		//Ascending order by SSN
		
		//return this.ssn-o.ssn;
		
		//Descending order by SSN
		
		//Ascending order by Name
		return o.ssn-this.ssn;
		
		
		
	}
	
	
	
	

}
