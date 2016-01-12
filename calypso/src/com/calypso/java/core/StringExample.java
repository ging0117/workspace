package com.calypso.java.core;

public class StringExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//single string builder immutable
		String date=new String("A");
		System.out.println("Value:"+date);
		date+="B";
		System.out.println("Value:"+date);
		date=date+"C";
		System.out.println("Value:"+date);
		//mutable
		StringBuilder date2= new StringBuilder(); 
		date2.append("A");
		System.out.println("Value2:"+date2);
		date2.append("B");
		System.out.println("Value2:"+date2);
		date2.append("C");
		System.out.println("Value2:"+date2);
		String line="I, live, in, Fremont, in, the, city ";
		System.out.println(line.indexOf(","));
		System.out.println(line.lastIndexOf(","));
		System.out.println(line.indexOf(":"));
		System.out.println(line.substring(1,10));
		//from 10 to end string
		System.out.println(line.substring(10));
		System.out.println(line.replace(",", ":"));	
		System.out.println(line.toUpperCase());
		System.out.println(line.length());
		char[] charDate=line.toCharArray();
		//length is variable
		System.out.println(charDate.length);
		for(char m:charDate)
		{
			System.out.println(m);
			
		}
		String[] cityArray=line.split(",");
		System.out.println(cityArray[3]);
		for(String d:cityArray)
		{
			
			System.out.println(d);
		}
	
		
		
		
	}

	
}
