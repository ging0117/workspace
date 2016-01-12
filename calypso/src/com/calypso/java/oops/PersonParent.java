package com.calypso.java.oops;

public class PersonParent extends UserAbstract {
	public String name;

	public int cal(int m)

	{
		return m + 2;

	}

	public int cal(int m1, int m2) {
		return m1 + m2;

	}
//override always happened on child class
	@Override
	public void data() {
		// TODO Auto-generated method stub
		System.out.println("Data defined");
	}
}
