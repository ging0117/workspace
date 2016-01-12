package com.calypso.java.core;

import com.calypso.java.core2.PersonCore;

public class UserCore {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		PersonCore pObj = new PersonCore();
		pObj.name = "Fremont";
		System.out.println("Age:" + pObj.getNum());
		System.out.println("Value:" + pObj.name);
		// object will not be viewed but static can be seen(can set up to class
		// level)
		// static will share resources
		PersonCore.city = "San Jose";
		System.out.println("City:" + pObj.city);
		// static is sharing resource level(class level)...non static is not
		// class level
		pObj.city = "NewwARK";
		System.out.println(PersonCore.city);
		int sum = PersonCore.caculate(3, 2);
		System.out.println("Sum:" + sum);
		System.out.println("Sum:" + PersonCore.caculate(8, 39));
		// public,static, final
		// pObj.state="Taxes";
		int data = 36;
		String data2 = "37";
		int num = Integer.parseInt(data2);
		System.out.println("Data:" + (data + num));
		// pObj is memory address call by reference object reference hashcode
		System.out.println("PersonCore" + pObj);

		if (data == num) {

			System.out.println("Equal");
		} else {

			System.out.println("Not Equal");

		}

		// heap memory these two new are two objects...they compared to their
		// different references
		Integer data3 = new Integer(24);
		Integer data4 = data3;
		System.out.println("Equal:" + (data3 == data4));
	}
}
