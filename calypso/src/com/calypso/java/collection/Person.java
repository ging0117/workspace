package com.calypso.java.collection;

public class Person implements Comparable<Person> {
	private int ssn;
	private String name;

	public Person(int d, String n) {

		this.ssn = d;
		this.name = n;

	}

	public int getSsn() {
		return ssn;
	}

	public void setSsn(int ssn) {
		this.ssn = ssn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// COMPARE TWO DIFFERENT OBJECTS
	@Override
	public int compareTo(Person o) {
		// TODO Auto-generated method stub
		// Accending order
		// return this.ssn-o.ssn;
		// Descendding order
		// return o.ssn-this.ssn;
		// return this.name.compareTo(o.name);
		// Decending order by name
		return o.name.compareTo(this.name);
	}

}
