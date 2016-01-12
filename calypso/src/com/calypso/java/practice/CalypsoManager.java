package com.calypso.java.practice;

public class CalypsoManager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CalypsoChild obj= new CalypsoChild();
        obj.city="Fremont";
        System.out.println(obj.calculate(4, 6));
        System.out.println(obj.city);
        //overload methods
        System.out.println(obj.calculate(8));
        obj.sayHello();
        CalypsoChild obj2=new CalypsoChild();
        obj2.city="fremont";
        //memory allocation...reference by address
        System.out.println(obj==obj);
        System.out.println(obj==obj2);
        System.out.println("obj:"+obj);
        System.out.println("obj2:"+obj2);
        //compare its values
        System.out.println(obj.city.equals(obj2.city));
        System.out.println(obj==obj2);
        
	}

}
