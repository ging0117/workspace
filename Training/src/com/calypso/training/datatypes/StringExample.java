package com.calypso.training.datatypes;

public class StringExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//MULTI-THREADED
		StringBuilder sb= new StringBuilder();
		sb.append("F");
		sb.append("G");
		sb.append("H");
		System.out.println("String data::"+sb.toString());
		String data="12, Ching, 94539, CA, USA";
		System.out.println("Comma index::"+data.indexOf(','));
		System.out.println("NAME index::"+data.indexOf("Ching"));
		System.out.println("Comma index::"+data.lastIndexOf(','));
		System.out.println("Replace index::"+data.replace("Ching","John"));
		System.out.println("Sub String::"+data.substring(data.indexOf(',')));
		System.out.println("Sub String no end index:: "+data.substring(data.indexOf(',')));
//Enhanced for loop
		char[] dataArr= data.toCharArray();
		for(char mydata:dataArr){
			System.out.println(mydata);
			
			
		}
		
		
		String [] dataArr2 = data.split(",");
		System.out.println("Name::"+dataArr2[1]);
		for(String mydata:dataArr2)
		{
			
			System.out.println(mydata);
		
		
		}
		String name=dataArr2[1];
		String name2=dataArr2[1].trim();
		System.out.println("name equal ::"+name.equals("Ching"));	
		System.out.println("name2  equal ::"+name2.equals("Ching"));	
	}

}
