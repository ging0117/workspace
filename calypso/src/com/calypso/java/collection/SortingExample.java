package com.calypso.java.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List personList = new ArrayList();
		personList.add(new Person(123, "Fremont"));
		personList.add(new Person(456, "Fremot"));
		personList.add(new Person(178, "Fredoom"));
		//using object comparable
		//Collections.sort(personList);
		//using person comparator
		Collections.sort(personList, new PersonComprator());
		
	printPerson(personList);	
	
	
	
	}

	private static void printPerson(List personList) {
		for (Object o : personList) {

			Person p = (Person) o;
			System.out.println("ssn:" + p.getSsn());
			System.out.println("name:" + p.getName());
			

		}

	}

}
