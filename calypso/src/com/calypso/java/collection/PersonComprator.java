package com.calypso.java.collection;

import java.util.Comparator;

public class PersonComprator implements Comparator<Person> {
	
	
	public int compare(Person obj, Person obj2)
	{
		return obj.getSsn()-obj2.getSsn();
		
		
	}

}
