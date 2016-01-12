package com.calypso.java.oops;
//single inheritance
public class UserChild extends PersonParent implements UserI {
	public int ssn;

	// static means class...non static means object
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PersonParent obj = new PersonParent();
		// obj only has parent paramenter
		System.out.println(obj.cal(10, 20));
		System.out.println(obj.cal(7));
		// obj2 will have name and ssn from parent class and child class
		//obj2 is from userchild()
		
		UserChild obj2 = new UserChild();
		
		System.out.println(obj2.cal(1, 3));
		obj2.print();
		obj2.data();
		obj2.printresult();
		// parent object to child can not be done
		// UserChild ob3 =new PersonParent();
		PersonParent obj4 = new UserChild();
		
		System.out.println(obj4.cal(3, 7));
		// obj4 will not have ssn...it only has parent parameter
		// obj4.
        
	}

	// this userchild class function will overwrite parent class cal function
	public int cal(int n1, int n2) {

		return n1 - n2;

	}


	public void print() {
		// TODO Auto-generated method stub
		System.out.println("Hello");

	}

}
